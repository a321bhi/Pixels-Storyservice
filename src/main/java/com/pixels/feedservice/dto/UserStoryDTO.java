package com.pixels.feedservice.dto;

import java.util.Date;

import org.bson.types.Binary;

public class UserStoryDTO {
	String storyId;
	Date timestamp;
	Date storyExpiryTimeStamp;
	Binary image;
	String storyByUsername;
	String imageAsBase64;

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Date getStoryExpiryTimeStamp() {
		return storyExpiryTimeStamp;
	}

	public void setStoryExpiryTimeStamp(Date storyExpiryTimeStamp) {
		this.storyExpiryTimeStamp = storyExpiryTimeStamp;
	}

	public Binary getImage() {
		return image;
	}

	public void setImage(Binary image) {
		this.image = image;
	}

	public String getStoryByUsername() {
		return storyByUsername;
	}

	public void setStoryByUsername(String storyByUsername) {
		this.storyByUsername = storyByUsername;
	}

	public String getImageAsBase64() {
		return imageAsBase64;
	}

	public void setImageAsBase64(String imageAsBase64) {
		this.imageAsBase64 = imageAsBase64;
	}

	public UserStoryDTO() {
		super();
	}

	public UserStoryDTO(String storyId, Date timestamp, Date storyExpiryTimeStamp, Binary image, String storyByUsername,
			String imageAsBase64) {
		super();
		this.storyId = storyId;
		this.timestamp = timestamp;
		this.storyExpiryTimeStamp = storyExpiryTimeStamp;
		this.image = image;
		this.storyByUsername = storyByUsername;
		this.imageAsBase64 = imageAsBase64;
	}

}
