package com.pixels.feedservice.service;

import java.util.Optional;

import com.pixels.feedservice.model.FeedPreference;

public interface FeedPreferenceService {
	public void setPreference(FeedPreference feedPreference);

	public Optional<FeedPreference> getPreferenceById(String username);

}
