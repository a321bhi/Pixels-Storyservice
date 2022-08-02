package com.pixels.storyservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = { UsernameNotFoundException.class })
	public ResponseEntity<String> usernameNotFoundExceptionHandler() {
		return new ResponseEntity<>("User not found or does not exist..", HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(value = { UserStoryUploadFailedException.class })
	public ResponseEntity<String> userStoryUploadFailedExceptionHandler() {
		return new ResponseEntity<>("Upload failed..", HttpStatus.CONFLICT);
	}

}
