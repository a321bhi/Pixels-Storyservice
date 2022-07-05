package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import com.pixels.feedservice.model.MediaMongo;

public interface MediaMongoService {
	public Optional<List<MediaMongo>> findByMediaTags(List<String> mediaTags);

	public Optional<MediaMongo> findMediaById(String mediaId);

	public MediaMongo addMedia(MediaMongo media);

	public void deleteMedia(MediaMongo media);

	public void deleteMediaById(String MediaId);

	public List<MediaMongo> findAllMedia();
	
	public List<MediaMongo> findTagsByQueryTag(String queryTag);
	
	public List<MediaMongo> getAllTags();
	
}
