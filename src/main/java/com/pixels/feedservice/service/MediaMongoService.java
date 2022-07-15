package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.pixels.feedservice.model.MediaMongo;

public interface MediaMongoService {
	public Optional<List<MediaMongo>> findByMediaTags(List<String> mediaTags);

	public Optional<MediaMongo> findMediaById(String mediaId);

	public MediaMongo addMedia(MediaMongo media);

	public void deleteMedia(MediaMongo media);

	public void deleteMediaById(String mediaId);

	public List<MediaMongo> findAllMedia();
	
	public List<MediaMongo> findTagsByQueryTag(String queryTag);
	
	public List<MediaMongo> getAllTags();

	Page<MediaMongo> findByMediaTags(List<String> mediaTags, int page, int size, String sortDir, String sort);
	
}
