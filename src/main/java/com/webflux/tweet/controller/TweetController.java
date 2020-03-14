package com.webflux.tweet.controller;

import com.webflux.tweet.model.Tweet;
import com.webflux.tweet.service.TweetService;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class TweetController {
    private final MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
    private TweetService service;

    public TweetController(TweetService service) {
        this.service = service;
    }

    @GetMapping(value = "/tweets")
    public Flux<Tweet> getAllTweets() {
        return service.all();
    }

    //     Tweets are Sent to the client as Server Sent Events
    @GetMapping(value = "/stream/tweets", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Tweet> streamAllTweets() {
        return service.all();
    }

    @PostMapping("/tweets")
    public Publisher<ResponseEntity<Tweet>> create(@RequestBody Tweet tweet) {
        return this.service
                .create(tweet.getText(), tweet.getComments())
                .map(p -> ResponseEntity.created(URI.create("/tweets/" + p.getId()))
                        .contentType(mediaType)
                        .build());
    }

}
