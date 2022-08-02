package com.pixels.storyservice.model;

import java.util.Date;
import java.util.Objects;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "userstories")
public class UserStory {

	@Id
	String storyId;

//	@Indexed(expireAfterSeconds = 86400)
	Date timestamp;

	Date storyExpiryTimestamp;
	Binary image;
	String storyByUsername;

	public Date getStoryExpiryTimestamp() {
		return storyExpiryTimestamp;
	}

	public void setStoryExpiryTimestamp(Date storyExpiryTimestamp) {
		this.storyExpiryTimestamp = storyExpiryTimestamp;
	}

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

	public UserStory() {
		super();
	}

	public UserStory(String storyId, Date timestamp, Binary image, String storyByUsername) {
		super();
		this.storyId = storyId;
		this.timestamp = timestamp;
		this.image = image;
		this.storyByUsername = storyByUsername;
	}

	@Override
	public int hashCode() {
		return Objects.hash(storyId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserStory other = (UserStory) obj;
		return Objects.equals(storyId, other.storyId);
	}

}
