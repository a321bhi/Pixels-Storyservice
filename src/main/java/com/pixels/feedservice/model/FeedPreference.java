package com.pixels.feedservice.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class FeedPreference {

	@Id
	String username;
	Set<String> feedPreference = new HashSet<>();

	public FeedPreference(String username) {
		super();
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Set<String> getFeedPreference() {
		return feedPreference;
	}

	public void setFeedPreference(Set<String> feedPreference) {
		this.feedPreference = feedPreference;
	}

	public FeedPreference(String username, Set<String> feedPreference) {
		super();
		this.username = username;
		this.feedPreference = feedPreference;
	}

	public FeedPreference() {
		super();
		// TODO Auto-generated constructor stub
	}

}
