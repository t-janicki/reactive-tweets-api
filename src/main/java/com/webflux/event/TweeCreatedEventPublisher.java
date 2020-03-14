package com.webflux.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

@Component
public class TweeCreatedEventPublisher implements ApplicationListener<TweetCreatedEvent>, Consumer<FluxSink<TweetCreatedEvent>> {
    private final Executor executor;
    private final BlockingQueue<TweetCreatedEvent> queue = new LinkedBlockingQueue<>();

    TweeCreatedEventPublisher(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void accept(FluxSink<TweetCreatedEvent> sink) {
        this.executor.execute(() -> {
            while (true)
                try {
                    TweetCreatedEvent event = queue.take();
                    sink.next(event);
                }
                catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }

    @Override
    public void onApplicationEvent(TweetCreatedEvent event) {
        this.queue.offer(event);
    }
}
