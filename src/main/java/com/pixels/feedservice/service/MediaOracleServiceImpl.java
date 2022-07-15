package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pixels.feedservice.doa.MediaOracleRespository;
import com.pixels.feedservice.model.MediaOracle;
import com.pixels.feedservice.model.PixelSenseUser;

@Service
public class MediaOracleServiceImpl implements MediaOracleService {

	@Autowired
	MediaOracleRespository mediaOracleRespository;

	@Override
	public List<String> getAllMediaOfOneUser(PixelSenseUser user) {
		return mediaOracleRespository.findAllMediaOfUser(user);
	}

	@Override
	public Optional<MediaOracle> findMediaById(String mediaId) {
		return mediaOracleRespository.findById(mediaId);
	}

	@Override
	public MediaOracle addMedia(MediaOracle media) {
		return mediaOracleRespository.save(media);
	}

	@Override
	public void deleteMediaById(String mediaId) {
		mediaOracleRespository.deleteById(mediaId);
	}

	@Override
	public void deleteMedia(MediaOracle media) {
		mediaOracleRespository.delete(media);
	}

}
