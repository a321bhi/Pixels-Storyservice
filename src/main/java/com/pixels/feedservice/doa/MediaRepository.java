package com.pixels.feedservice.doa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pixels.feedservice.model.MediaMongo;

@Repository
public interface MediaRepository extends MongoRepository<MediaMongo, String> {
	@Query("{ 'mediaTags':{$in:?0 }}")
	public Optional<List<MediaMongo>> findByMediaTags(List<String> mediaTags);
	
	@Query(value="{'mediaTags' : /^?0/}", fields="{mediaTags : 1, _id : 0}")
	public List<MediaMongo> findMediaTagsByQuery(String queryTag);
	
	@Query(value="{}", fields="{mediaTags : 1, _id : 0}")
	public List<MediaMongo> findAllMediaTags();

// pageable queries
	public Page<MediaMongo> findAll(Pageable pageable);
	
	@Query("{ 'mediaTags':{$in:?0 }}")
	public Page<MediaMongo> findByMediaTags(List<String> mediaTags,Pageable pageable);
}
