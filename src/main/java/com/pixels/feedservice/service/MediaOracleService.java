package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import com.pixels.feedservice.model.MediaOracle;
import com.pixels.feedservice.model.PixelSenseUser;


public interface MediaOracleService {
	public List<String> getAllMediaOfOneUser(PixelSenseUser user);
	public Optional<MediaOracle> findMediaById(String mediaId);
	public MediaOracle addMedia(MediaOracle media);
	public void deleteMedia(MediaOracle media);
	public void deleteMediaById(String mediaId);
	
}
