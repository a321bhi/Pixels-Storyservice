package com.pixels.feedservice.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResult {

	List<String> mediaTagsOutput = new ArrayList<>();
	List<String> usernameOutput = new ArrayList<>();
	public List<String> getMediaTagsOutput() {
		return mediaTagsOutput;
	}
	public void setMediaTagsOutput(List<String> mediaTagsOutput) {
		this.mediaTagsOutput = mediaTagsOutput;
	}
	public List<String> getUsernameOutput() {
		return usernameOutput;
	}
	public void setUsernameOutput(List<String> usernameOutput) {
		this.usernameOutput = usernameOutput;
	}
	public SearchResult(List<String> mediaTagsOutput, List<String> usernameOutput) {
		super();
		this.mediaTagsOutput = mediaTagsOutput;
		this.usernameOutput = usernameOutput;
	}
	
}
