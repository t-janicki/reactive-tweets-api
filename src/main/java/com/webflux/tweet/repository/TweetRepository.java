package com.webflux.tweet.repository;


import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import com.webflux.tweet.model.Tweet;

@Repository
public interface TweetRepository extends ReactiveMongoRepository<Tweet, String> {
}
