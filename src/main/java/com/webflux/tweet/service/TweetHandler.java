package com.webflux.tweet.service;

import com.webflux.tweet.model.Tweet;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class TweetHandler {
    private final TweetService tweetService;

    TweetHandler(TweetService tweetService) {
        this.tweetService = tweetService;
    }

    Mono<ServerResponse> getById(ServerRequest request) {
        return defaultReadResponse(tweetService.get(id(request)));
    }

    Mono<ServerResponse> all(ServerRequest request) {
        return defaultReadResponse(tweetService.all());
    }

    Mono<ServerResponse> create(ServerRequest request) {
        Flux<Tweet> tweet = request
                .bodyToFlux(Tweet.class)
                .flatMap(toWrite -> tweetService.create(toWrite.getText()));
        return defaultWriteResponse(tweet);
    }

    private static Mono<ServerResponse> defaultWriteResponse(Publisher<Tweet> tweets) {
        return Mono
                .from(tweets)
                .flatMap(t -> ServerResponse
                        .created(URI.create("/tweets/" + t.getId()))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .build()
                );
    }

    private static Mono<ServerResponse> defaultReadResponse(Publisher<Tweet> tweets) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(tweets, Tweet.class);
    }

    private static String id(ServerRequest r) {
        return r.pathVariable("id");
    }
}
