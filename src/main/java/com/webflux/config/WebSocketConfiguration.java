package com.webflux.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webflux.comment.event.CommentCreatedEvent;
import com.webflux.comment.event.CommentCreatedEventPublisher;
import com.webflux.comment.event.CustomWebsocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
class WebSocketConfiguration {

    @Bean
    Executor executor() {
        return Executors.newSingleThreadExecutor();
    }

    @Bean
    HandlerMapping handlerMapping(CustomWebsocketHandler wsh) {
        return new SimpleUrlHandlerMapping() {
            {
//                setUrlMap(Collections.singletonMap("/ws/tweets", wsh));
                System.out.println("Handler mapping");
                setUrlMap(Map.of(
                        "/ws/tweets", wsh,
                        "/ws/comments", wsh
                ));
                setOrder(10);
            }
        };
    }

    @Bean
    WebSocketHandlerAdapter webSocketHandlerAdapter() {
        return new WebSocketHandlerAdapter();
    }

//    @Bean
//    WebSocketHandler webSocketHandler(ObjectMapper objectMapper,
//                                      TweetCreatedEventPublisher eventPublisher) {
//
//        Flux<TweetCreatedEvent> publish = Flux.create(eventPublisher).share();
//
//        return session -> {
//
//            Flux<WebSocketMessage> messageFlux = publish.map(evt -> {
//                try {
//                    Tweet tweet = (Tweet) evt.getSource();
//                    Map<String, String> data = new HashMap<>();
//                    data.put("id", tweet.getId());
//                    return objectMapper.writeValueAsString(data);
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }).map(session::textMessage);
//
//            return session.send(messageFlux);
//        };
//    }

//    @Bean
//    WebSocketHandler webSocketHandler(ObjectMapper objectMapper,
//                                      CommentCreatedEventPublisher eventPublisher) {
////        Flux<CommentCreatedEvent> share = Flux.create(eventPublisher).share();
//        System.out.println("Web Socket handler");
//       return session -> {
//           Flux<WebSocketMessage> map = Flux.create(eventPublisher).share()
//                   .map(pce -> {
//                       try {
//                           return objectMapper.writeValueAsString(pce);
//                       } catch (JsonProcessingException e) {
//                           throw  new RuntimeException();
//                       }
//                   })
//                   .map(session::textMessage);
//           return session.send(map);
//       };
//    }

}