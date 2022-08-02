package com.pixels.storyservice.service;

import java.util.List;

import com.pixels.storyservice.model.UserStory;

public interface UserStoryService {
	public List<UserStory> getUserStoryByUsername(String username);

	public void addUserStory(UserStory userStory);

	public List<UserStory> getActiveStoriesByUsername(String username);

	public List<UserStory> getArchivedStoriesByUsername(String username);

}
