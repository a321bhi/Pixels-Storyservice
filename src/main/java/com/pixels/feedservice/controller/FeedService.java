package com.pixels.feedservice.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pixels.feedservice.dto.Payload;
import com.pixels.feedservice.dto.ResponsePayload;
import com.pixels.feedservice.dto.SearchResult;
import com.pixels.feedservice.dto.UserStoryDTO;
import com.pixels.feedservice.exception.UserStoryUploadFailedException;
import com.pixels.feedservice.exception.UsernameNotFoundException;
import com.pixels.feedservice.model.FeedPreference;
import com.pixels.feedservice.model.MediaMongo;
import com.pixels.feedservice.model.MediaOracle;
import com.pixels.feedservice.model.PixelSenseUser;
import com.pixels.feedservice.model.UserStory;
import com.pixels.feedservice.service.FeedPreferenceServiceImpl;
import com.pixels.feedservice.service.MediaMongoServiceImpl;
import com.pixels.feedservice.service.MediaOracleServiceImpl;
import com.pixels.feedservice.service.UserServiceImpl;
import com.pixels.feedservice.service.UserStoryServiceImpl;

@RestController
@RequestMapping("/feed")
//@CrossOrigin(origins = "*", allowedHeaders = { "*" })
public class FeedService {

	@Autowired
	UserStoryServiceImpl userStoryServiceImpl;

	@Autowired
	MediaOracleServiceImpl mediaOracleServiceImpl;

	@Autowired
	MediaMongoServiceImpl mediaMongoServiceImpl;

	@Autowired
	FeedPreferenceServiceImpl feedPreferenceServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@GetMapping(value = "/{mediaId}")
	public ResponsePayload getTags(@PathVariable String mediaId) {
		Optional<MediaMongo> outputOpt = mediaMongoServiceImpl.findMediaById(mediaId);
		if (outputOpt.isPresent()) {
			MediaMongo outputMedia = outputOpt.get();
			Payload payload = new Payload(outputMedia.getMediaId(), outputMedia.getMediaDate(),
					outputMedia.getMediaTags(), outputMedia.getMediaCaption(), outputMedia.getMediaEncodedData());

			ResponsePayload temporaryResponsePayload;
			Optional<MediaOracle> tempOptMedia;
			MediaOracle tempMedia;
			Set<String> usersWhoLikedThisMedia = new HashSet<>();
			temporaryResponsePayload = new ResponsePayload(payload);
			tempOptMedia = mediaOracleServiceImpl.findMediaById(temporaryResponsePayload.getMediaId());
			if (tempOptMedia.isPresent()) {
				tempMedia = tempOptMedia.get();
				for (PixelSenseUser tempUser : tempMedia.getLikedBy()) {
					usersWhoLikedThisMedia.add(tempUser.getUserName());
				}
				temporaryResponsePayload.setLikedBy(usersWhoLikedThisMedia);
				PixelSenseUser usernamePostedBy = tempMedia.getMediaPostedBy();
				temporaryResponsePayload.setUsernamePostedBy(usernamePostedBy.getUserName());
				temporaryResponsePayload.setMediaComments(tempMedia.getMediaComments());
				temporaryResponsePayload.refactorMediaComments();
				Optional<MediaMongo> mediaProfilePicTempOpt = mediaMongoServiceImpl
						.findMediaById(usernamePostedBy.getProfilePicId());
				MediaMongo mediaProfilePicTemp = null;
				if (mediaProfilePicTempOpt.isPresent()) {
					mediaProfilePicTemp = mediaProfilePicTempOpt.get();
				}
				temporaryResponsePayload
						.setProfilePicOfUsernamePostedByBase64(mediaProfilePicTemp.getMediaEncodedData());
			}
			return temporaryResponsePayload;

		} else {
			return new ResponsePayload();
		}
	}

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
					for (PixelSenseUser tempUser : tempMedia.getLikedBy()) {
						usersWhoLikedThisMedia.add(tempUser.getUserName());
					}
					temporaryResponsePayload.setLikedBy(usersWhoLikedThisMedia);
					PixelSenseUser usernamePostedBy = tempMedia.getMediaPostedBy();
					temporaryResponsePayload.setUsernamePostedBy(usernamePostedBy.getUserName());
					temporaryResponsePayload.setMediaComments(tempMedia.getMediaComments());
					temporaryResponsePayload.refactorMediaComments();
					Optional<MediaMongo> mediaProfilePicTempOpt = mediaMongoServiceImpl
							.findMediaById(usernamePostedBy.getProfilePicId());

					MediaMongo mediaProfilePicTemp = null;
					if (mediaProfilePicTempOpt.isPresent()) {
						mediaProfilePicTemp = mediaProfilePicTempOpt.get();
					}
					temporaryResponsePayload
							.setProfilePicOfUsernamePostedByBase64(mediaProfilePicTemp.getMediaEncodedData());
					listOfResponsePayload.add(temporaryResponsePayload);
				}
			}

			return listOfResponsePayload;

		} else {
			return new ArrayList<>();
		}
	}

	@PostMapping("/feed-preference")
	public ResponseEntity<String> setPreference(@RequestBody FeedPreference feedPreference) {
		feedPreferenceServiceImpl.setPreference(feedPreference);
		return new ResponseEntity<>("preference set", HttpStatus.OK);
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
		mediaTagsOutput.stream().map(MediaMongo::getMediaTags).forEach(t -> {
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

	@GetMapping("/feed-paginated/")
	public List<ResponsePayload> getFeedsPaginated(@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("sortDir") String sortDir, @RequestParam("sort") String sort,
			@RequestParam("tags") List<String> tags) {
		Page<MediaMongo> outputFeedPage = mediaMongoServiceImpl.findByMediaTags(tags, page, size, sortDir, sort);
		List<MediaMongo> outputFeed = outputFeedPage.getContent();
		if (!outputFeed.isEmpty()) {

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
					for (PixelSenseUser tempUser : tempMedia.getLikedBy()) {
						usersWhoLikedThisMedia.add(tempUser.getUserName());
					}
					temporaryResponsePayload.setLikedBy(usersWhoLikedThisMedia);
					PixelSenseUser usernamePostedBy = tempMedia.getMediaPostedBy();
					temporaryResponsePayload.setUsernamePostedBy(usernamePostedBy.getUserName());
					Optional<MediaMongo> mediaProfilePicTempOpt = mediaMongoServiceImpl
							.findMediaById(usernamePostedBy.getProfilePicId());
					MediaMongo mediaProfilePicTemp = null;
					if (mediaProfilePicTempOpt.isPresent()) {
						mediaProfilePicTemp = mediaProfilePicTempOpt.get();
					}
					temporaryResponsePayload
							.setProfilePicOfUsernamePostedByBase64(mediaProfilePicTemp.getMediaEncodedData());
					temporaryResponsePayload.setMediaComments(tempMedia.getMediaComments());
					temporaryResponsePayload.refactorMediaComments();
					listOfResponsePayload.add(temporaryResponsePayload);
				}
			}

			return listOfResponsePayload;

		} else {
			return new ArrayList<>();
		}
	}

	@PostMapping(value = "/story", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadStory(@ModelAttribute Payload payload) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		Optional<PixelSenseUser> optionalUser = userServiceImpl.findUserById(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException();
		}
		PixelSenseUser user = optionalUser.get();
		UserStory userStory = new UserStory();
		userStory.setTimestamp(Date.from(Instant.now()));
		userStory.setStoryByUsername(user.getUserName());
		userStory.setStoryExpiryTimestamp(Date.from(Instant.now().plusSeconds(86400)));
		try {
			userStory.setImage(new Binary(BsonBinarySubType.BINARY, payload.getImage().getBytes()));
		} catch (IOException e) {
			throw new UserStoryUploadFailedException();
		}
		userStoryServiceImpl.addUserStory(userStory);
		return new ResponseEntity<>("Story added!", HttpStatus.OK);

	}

	@GetMapping(value = "/stories/{username}")
	public List<UserStoryDTO> getStories(@PathVariable String username) {
		Optional<PixelSenseUser> optionalUser = userServiceImpl.findUserById(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException();
		}
		PixelSenseUser user = optionalUser.get();

		List<UserStory> listOfUserStory = userStoryServiceImpl.getActiveStoriesByUsername(user.getUserName());
		List<UserStoryDTO> listOfUserStoryDTOs = new ArrayList<>();
		UserStoryDTO userStoryDTOTemp;
		for (UserStory userStoryTemp : listOfUserStory) {
			userStoryDTOTemp = new UserStoryDTO();
			userStoryDTOTemp.setStoryByUsername(userStoryTemp.getStoryByUsername());
			userStoryDTOTemp.setStoryExpiryTimeStamp(userStoryTemp.getStoryExpiryTimestamp());
			userStoryDTOTemp.setTimestamp(userStoryTemp.getTimestamp());
			userStoryDTOTemp.setStoryId(userStoryTemp.getStoryId());
			userStoryDTOTemp.setImageAsBase64(Base64.getEncoder().encodeToString(userStoryTemp.getImage().getData()));
			listOfUserStoryDTOs.add(userStoryDTOTemp);
		}
		return listOfUserStoryDTOs;
	}

	@GetMapping(value = "/archived-stories/{username}")
	public List<UserStoryDTO> getArchivedStories(@PathVariable String username) {
		Optional<PixelSenseUser> optionalUser = userServiceImpl.findUserById(username);
		if (optionalUser.isEmpty()) {
			throw new UsernameNotFoundException();
		}
		PixelSenseUser user = optionalUser.get();

		List<UserStory> listOfUserStory = userStoryServiceImpl.getArchivedStoriesByUsername(user.getUserName());
		List<UserStoryDTO> listOfUserStoryDTOs = new ArrayList<>();
		UserStoryDTO userStoryDTOTemp;
		for (UserStory userStoryTemp : listOfUserStory) {
			userStoryDTOTemp = new UserStoryDTO();
			userStoryDTOTemp.setStoryByUsername(userStoryTemp.getStoryByUsername());
			userStoryDTOTemp.setStoryExpiryTimeStamp(userStoryTemp.getStoryExpiryTimestamp());
			userStoryDTOTemp.setTimestamp(userStoryTemp.getTimestamp());
			userStoryDTOTemp.setStoryId(userStoryTemp.getStoryId());
			userStoryDTOTemp.setImageAsBase64(Base64.getEncoder().encodeToString(userStoryTemp.getImage().getData()));
			listOfUserStoryDTOs.add(userStoryDTOTemp);
		}
		return listOfUserStoryDTOs;
	}
}
