package com.webflux.tweet.event;

import com.webflux.tweet.model.Tweet;
import org.springframework.context.ApplicationEvent;

public class TweetCreatedEvent extends ApplicationEvent {
    public TweetCreatedEvent(Tweet source) {
        super(source);
    }
}
