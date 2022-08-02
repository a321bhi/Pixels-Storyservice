package com.pixels.storyservice.controller;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pixels.storyservice.dto.Payload;
import com.pixels.storyservice.dto.UserStoryDTO;
import com.pixels.storyservice.exception.UserStoryUploadFailedException;
import com.pixels.storyservice.model.UserStory;
import com.pixels.storyservice.service.UserStoryServiceImpl;

@RestController
@RequestMapping("/story")
public class StoryService {

	@Autowired
	UserStoryServiceImpl userStoryServiceImpl;

	@PostMapping(value = "/story", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadStory(@ModelAttribute Payload payload,
			@RequestHeader("username") String usernameFromGateway) {

		UserStory userStory = new UserStory();
		userStory.setTimestamp(Date.from(Instant.now()));
		userStory.setStoryByUsername(usernameFromGateway);
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

		List<UserStory> listOfUserStory = userStoryServiceImpl.getActiveStoriesByUsername(username);
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
	public List<UserStoryDTO> getArchivedStories(@PathVariable String username,
			@RequestHeader("username") String usernameFromGateway) {
		List<UserStory> listOfUserStory = userStoryServiceImpl.getArchivedStoriesByUsername(usernameFromGateway);
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
