package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import com.pixels.feedservice.model.PixelSenseUser;



public interface UserService {
	public List<String> getUsernameBasedOnQuery(String username);
	public Optional<PixelSenseUser> findUserById(String username);
}
