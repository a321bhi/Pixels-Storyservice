package com.pixels.storyservice.exception;

import java.io.Serializable;

public class UserStoryUploadFailedException extends RuntimeException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserStoryUploadFailedException() {
		super();
	}

	public UserStoryUploadFailedException(String message) {
		super(message);
	}

}
