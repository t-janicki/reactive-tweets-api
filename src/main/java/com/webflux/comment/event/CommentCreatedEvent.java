package com.webflux.comment.event;

import org.springframework.context.ApplicationEvent;

public class CommentCreatedEvent extends ApplicationEvent {
        public CommentCreatedEvent(Object source) {
            super(source);
    }
}
