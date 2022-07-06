package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixels.feedservice.doa.UserRepository;
import com.pixels.feedservice.model.PixelSenseUser;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public List<String> getUsernameBasedOnQuery(String queryUsername) {
		return userRepository.getUsername(queryUsername);
	}

	@Override
	public Optional<PixelSenseUser> findUserById(String username) {
		return userRepository.findById(username);
	}

}
