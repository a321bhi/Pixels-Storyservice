package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixels.feedservice.doa.MediaOracleRespository;
import com.pixels.feedservice.model.MediaOracle;
import com.pixels.feedservice.model.PixelSenseUser;

@Service
public class MediaOracleServiceImpl implements MediaOracleService{

	@Autowired
	MediaOracleRespository mediaOracleRespository;
	
	@Override
	public List<String> getAllMediaOfOneUser(PixelSenseUser user) {
		List<String> listOfMedia = mediaOracleRespository.findAllMediaOfUser(user);
		return listOfMedia;
	}

	@Override
	public Optional<MediaOracle> findMediaById(String mediaId) {
		Optional<MediaOracle> mediaOptional = mediaOracleRespository.findById(mediaId);
		return mediaOptional;
	}

	@Override
	public MediaOracle addMedia(MediaOracle media) {
		MediaOracle savedMedia = mediaOracleRespository.save(media);
		return savedMedia;
	}

	@Override
	public void deleteMediaById(String MediaId) {
		mediaOracleRespository.deleteById(MediaId);
		return;
	}

	@Override
	public void deleteMedia(MediaOracle media) {
		mediaOracleRespository.delete(media);
		return;
	}

}
