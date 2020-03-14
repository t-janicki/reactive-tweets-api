package com.webflux.tweet.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class TweetControllerConfig {

    @Bean
    RouterFunction<ServerResponse> routes(TweetHandler tweetHandler) {
        return route(i(GET("/tweets")), tweetHandler::all)
                .andRoute(i(GET("/tweets/{id}")), tweetHandler::getById)
                .andRoute(i(POST("/tweets")), tweetHandler::create);
    }

    private static RequestPredicate i(RequestPredicate target) {
        return new CaseInsensitiveRequestPredicate(target);
    }
}
