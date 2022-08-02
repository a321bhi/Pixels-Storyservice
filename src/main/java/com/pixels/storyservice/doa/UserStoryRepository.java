package com.pixels.storyservice.doa;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.pixels.storyservice.model.UserStory;

@Repository
public interface UserStoryRepository extends MongoRepository<UserStory, String> {

	public List<UserStory> getUserStoryByStoryByUsername(String username);

	@Query("{ $and: [" + "{'storyByUsername': ?0}," + "{'storyExpiryTimestamp':{$gte: ?1}}" + "]}")
	public List<UserStory> getActiveStoriesByUsername(String username, Date date);

	@Query("{ $and: [" + "{'storyByUsername': ?0}," + "{'storyExpiryTimestamp':{$lte: ?1}}" + "]}")
	public List<UserStory> getArchivedStoriesByUsername(String username, Date date);
}