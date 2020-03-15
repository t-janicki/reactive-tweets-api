package com.webflux.comment.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomWebsocketHandler implements WebSocketHandler {
    private CommentCreatedEventPublisher eventPublisher;
    private ObjectMapper objectMapper;


    public CustomWebsocketHandler(CommentCreatedEventPublisher eventPublisher,
                                  ObjectMapper objectMapper) {
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(WebSocketSession s) {
        Flux<WebSocketMessage> messageFlux = Flux.create(eventPublisher).share()
                .map(pce -> {
                    System.out.println("Web Socket handler");
                    try {
                        return objectMapper.writeValueAsString(pce);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException();
                    }
                })
                .map(s::textMessage);
        return s.send(messageFlux);
    }
}
