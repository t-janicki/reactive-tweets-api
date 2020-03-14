package com.webflux.comment.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import reactor.core.publisher.FluxSink;

import java.util.function.Consumer;

public interface CreatedEventPublisher<T extends ApplicationEvent> extends ApplicationListener<T>, Consumer<FluxSink<T>> {
}
