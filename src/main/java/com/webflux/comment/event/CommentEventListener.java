package com.webflux.comment.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CommentEventListener implements ApplicationListener<CommentCreatedEvent> {
    @Override
    public void onApplicationEvent(CommentCreatedEvent event) {
        System.out.println("Received spring custom event - " + event.getSource());
    }
}
