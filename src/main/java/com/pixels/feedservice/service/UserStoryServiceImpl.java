package com.pixels.feedservice.service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixels.feedservice.doa.UserStoryRepository;
import com.pixels.feedservice.model.UserStory;

@Service
public class UserStoryServiceImpl implements UserStoryService {

	@Autowired
	UserStoryRepository userStoryRepository;
	@Override
	public List<UserStory> getUserStoryByUsername(String username) {
		return userStoryRepository.getUserStoryByStoryByUsername(username);
	}

	@Override
	public void addUserStory(UserStory userStory) {
		userStoryRepository.insert(userStory);
	}

	@Override
	public List<UserStory> getActiveStoriesByUsername(String username) {
		return userStoryRepository.getActiveStoriesByUsername(username,Date.from(Instant.now()));
	}

	@Override
	public List<UserStory> getArchivedStoriesByUsername(String username) {
		return userStoryRepository.getArchivedStoriesByUsername(username, Date.from(Instant.now()));
	}

}
