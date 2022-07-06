package com.pixels.feedservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.pixels.feedservice.doa.MediaRepository;
import com.pixels.feedservice.model.MediaMongo;

@Service
public class MediaMongoServiceImpl implements MediaMongoService {

	@Autowired
	MediaRepository mediaRepository;

	@Override
	public Optional<MediaMongo> findMediaById(String mediaId) {
		Optional<MediaMongo> mediaOptional = mediaRepository.findById(mediaId);
		return mediaOptional;
	}

	@Override
	public MediaMongo addMedia(MediaMongo media) {
		MediaMongo savedMedia = mediaRepository.save(media);
		return savedMedia;
	}

	@Override
	public void deleteMediaById(String MediaId) {
		mediaRepository.deleteById(MediaId);
		return;
	}

	@Override
	public void deleteMedia(MediaMongo media) {
		mediaRepository.delete(media);
		return;
	}

	@Override
	public Optional<List<MediaMongo>> findByMediaTags(List<String> mediaTags) {
		return mediaRepository.findByMediaTags(mediaTags);
	}

	@Override
	public List<MediaMongo> findAllMedia() {
		return mediaRepository.findAll();
	}

	@Override
	public List<MediaMongo> findTagsByQueryTag(String queryTag) {
		return mediaRepository.findMediaTagsByQuery(queryTag);
	}

	@Override
	public List<MediaMongo> getAllTags() {
		return mediaRepository.findAllMediaTags();
	}

	//pageable
	@Override
	public Page<MediaMongo> findByMediaTags(List<String> mediaTags, int page, int size, String sortDir, String sort) {
		  PageRequest pageReq
	        = PageRequest.of(page, size, Sort.Direction.fromString(sortDir), sort);

		return  mediaRepository.findByMediaTags(mediaTags, pageReq);
		
	}
	
}

