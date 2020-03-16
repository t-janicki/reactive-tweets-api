package com.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Bean
    Executor executor() {
        return Executors.newSingleThreadExecutor();
    }

//    @Bean
//    HandlerMapping handlerMapping(CustomWebsocketHandler wsh) {
//        return new SimpleUrlHandlerMapping() {
//            {
//                System.out.println("Handler mapping");
//                setUrlMap(Map.of("/ws/comments", wsh));
//                setOrder(10);
//            }
//        };
//    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocketApp").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue/");
        registry.setApplicationDestinationPrefixes("/app");
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

}