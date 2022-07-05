package com.pixels.feedservice.doa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pixels.feedservice.model.FeedPreference;



@Repository
public interface FeedPreferenceRepository extends MongoRepository<FeedPreference, String> {

}
