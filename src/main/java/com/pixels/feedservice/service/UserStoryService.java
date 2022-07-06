package com.pixels.feedservice.service;

import java.util.Date;
import java.util.List;

import com.pixels.feedservice.model.UserStory;

public interface UserStoryService {
	public List<UserStory> getUserStoryByUsername(String username);
	public void addUserStory(UserStory userStory);
	
	public List<UserStory> getActiveStoriesByUsername(String username);
	
	
	public List<UserStory> getArchivedStoriesByUsername(String username);
	
}
