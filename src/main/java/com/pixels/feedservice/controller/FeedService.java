package com.pixels.feedservice.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixels.feedservice.model.FeedPreference;
import com.pixels.feedservice.model.MediaMongo;
import com.pixels.feedservice.model.MediaOracle;
import com.pixels.feedservice.model.Payload;
import com.pixels.feedservice.model.PixelSenseUser;
import com.pixels.feedservice.model.ResponsePayload;
import com.pixels.feedservice.model.SearchResult;
import com.pixels.feedservice.service.FeedPreferenceServiceImpl;
import com.pixels.feedservice.service.MediaMongoServiceImpl;
import com.pixels.feedservice.service.MediaOracleServiceImpl;
import com.pixels.feedservice.service.UserServiceImpl;

@RestController
@RequestMapping("/media")
@CrossOrigin(origins = "*", allowedHeaders = { "*" })
public class FeedService {

	@Autowired
	MediaOracleServiceImpl mediaOracleServiceImpl;

	@Autowired
	MediaMongoServiceImpl mediaMongoServiceImpl;

	@Autowired
	FeedPreferenceServiceImpl feedPreferenceServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping(value = "/tags")
	public List<String> getTags() {
		List<String> outputPayload = new ArrayList<>();
		List<MediaMongo> output = mediaMongoServiceImpl.getAllTags();
		for (MediaMongo media : output) {
			outputPayload.addAll(media.getMediaTags());
		}
		return outputPayload;
	}

	@GetMapping("/feed/{tags}")
	public List<ResponsePayload> getFeeds(@PathVariable List<String> tags) {
//		String usernameRequestedBy = SecurityContextHolder.getContext().getAuthentication().getName();
//		Optional<PixelSenseUser> optionalUser = userServiceImpl.findUser(username);
//		if (optionalUser.isEmpty()) {
//			throw new UsernameNotFoundException();
//		}

		Optional<List<MediaMongo>> outputFeedOpt = mediaMongoServiceImpl.findByMediaTags(tags);
		if (outputFeedOpt.isPresent()) {
			List<MediaMongo> outputFeed = outputFeedOpt.get();
			List<Payload> outputPayload = new ArrayList<>();
			for (MediaMongo output : outputFeed) {
				Payload payload = new Payload(output.getMediaId(), output.getMediaDate(), output.getMediaTags(),
						output.getMediaCaption(), output.getMediaEncodedData());
				outputPayload.add(payload);
			}

			List<ResponsePayload> listOfResponsePayload = new ArrayList<>();

			ResponsePayload temporaryResponsePayload;
			Optional<MediaOracle> tempOptMedia;
			MediaOracle tempMedia;
			for (Payload tempPayload : outputPayload) {
				Set<String> usersWhoLikedThisMedia = new HashSet<>();
				temporaryResponsePayload = new ResponsePayload(tempPayload);
				tempOptMedia = mediaOracleServiceImpl.findMediaById(temporaryResponsePayload.getMediaId());
				if (tempOptMedia.isPresent()) {
					tempMedia = tempOptMedia.get();
					// usersWhoLikedThisMedia =
					// mediaRepository.findUsersWhoLikedThisMedia(tempMedia);
					for (PixelSenseUser tempUser : tempMedia.getLikedBy()) {
						usersWhoLikedThisMedia.add(tempUser.getUserName());
					}
					temporaryResponsePayload.setLikedBy(usersWhoLikedThisMedia);
					PixelSenseUser usernamePostedBy = tempMedia.getMediaPostedBy();
					temporaryResponsePayload.setUsernamePostedBy(usernamePostedBy.getUserName());
					Optional<MediaMongo> mediaProfilePicTempOpt = mediaMongoServiceImpl
							.findMediaById(usernamePostedBy.getProfilePicId());
					MediaMongo mediaProfilePicTemp = mediaProfilePicTempOpt.get();
					temporaryResponsePayload
							.setProfilePicOfUsernamePostedByBase64(mediaProfilePicTemp.getMediaEncodedData());
					temporaryResponsePayload.setMediaComments(tempMedia.getMediaComments());
					temporaryResponsePayload.refactorMediaComments();
					listOfResponsePayload.add(temporaryResponsePayload);
				}
			}

			return listOfResponsePayload;

		} else {
			return new ArrayList<ResponsePayload>();
		}
	}

	@PostMapping("/feed-preference")
	public ResponseEntity<String> setPreference(@RequestBody FeedPreference feedPreference) {
		feedPreferenceServiceImpl.setPreference(feedPreference);
		return new ResponseEntity<String>("preference set", HttpStatus.OK);
	}

	@GetMapping("/feed-preference/{username}")
	public FeedPreference updatePreference(@PathVariable String username) {

		Optional<FeedPreference> feedPreferenceByUserIdOpt = feedPreferenceServiceImpl.getPreferenceById(username);
		if (feedPreferenceByUserIdOpt.isPresent()) {
			return feedPreferenceByUserIdOpt.get();
		}
		return new FeedPreference(username);
	}

	@GetMapping("/search/{query}")
	public SearchResult getSearchResults(@PathVariable String query) {
		List<MediaMongo> mediaTagsOutput = mediaMongoServiceImpl.findTagsByQueryTag(query);
		List<String> usernameOutput = userServiceImpl.getUsernameBasedOnQuery(query);
		List<String> mediaTags = new ArrayList<>();
		mediaTagsOutput.stream().map(media -> media.getMediaTags()).forEach(t -> {
			for (String checkingForQueryString : t) {
				if (checkingForQueryString.contains(query) && !mediaTags.contains(query)) {
					mediaTags.add(checkingForQueryString);
				}
			}
		});
		SearchResult searchResult = new SearchResult(mediaTags, usernameOutput);
		;
		return searchResult;
	}
}
