package com.pixels.feedservice.doa;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pixels.feedservice.model.MediaMongo;
import com.pixels.feedservice.model.UserStory;

@Repository
public interface UserStoryRepository extends MongoRepository<UserStory, String> {
	
	public List<UserStory> getUserStoryByStoryByUsername(String username);

	@Query("{ $and: ["
			+ "{'storyByUsername': ?0},"
			+ "{'storyExpiryTimestamp':{$gte: ?1}}"
			+ "]}")
	public List<UserStory> getActiveStoriesByUsername(String username, Date date);
	
	@Query("{ $and: ["
			+ "{'storyByUsername': ?0},"
			+ "{'storyExpiryTimestamp':{$lte: ?1}}"
			+ "]}")
	public List<UserStory> getArchivedStoriesByUsername(String username, Date date);
}