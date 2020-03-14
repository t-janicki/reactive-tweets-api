package com.webflux.event;

import com.webflux.model.Tweet;
import org.springframework.context.ApplicationEvent;

public class TweetCreatedEvent extends ApplicationEvent {
    public TweetCreatedEvent(Tweet source) {
        super(source);
    }
}
