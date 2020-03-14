package com.webflux.tweet.service;


import com.webflux.tweet.event.TweetCreatedEvent;
import com.webflux.tweet.model.Tweet;
import com.webflux.tweet.repository.TweetRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class TweetService {
    private final ApplicationEventPublisher publisher;
    private final TweetRepository tweetRepository;

    TweetService(ApplicationEventPublisher publisher, TweetRepository tweetRepository) {
        this.publisher = publisher;
        this.tweetRepository = tweetRepository;
    }

    public Flux<Tweet> all() {
        return tweetRepository.findAll();
    }

    public Mono<Tweet> get(String id) {
        return tweetRepository.findById(id);
    }

    public Mono<Tweet> create(String text) {
        return tweetRepository
                .save(new Tweet(text))
                .doOnSuccess(tweet -> this.publisher.publishEvent(new TweetCreatedEvent(tweet)));
    }

    public Mono<Tweet> update(String id, String text) {
        return tweetRepository
                .findById(id)
                .map(tweet -> new Tweet(tweet.getId(), text))
                .flatMap(tweetRepository::save);
    }

    public Mono<Tweet> delete(String id) {
        return tweetRepository
                .findById(id)
                .flatMap(tweet -> tweetRepository.deleteById(tweet.getId()).thenReturn(tweet));
    }
}
