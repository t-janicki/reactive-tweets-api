package com.webflux.comment.event;

import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import reactor.core.publisher.FluxSink;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class CommentCreatedEventPublisher implements CreatedEventPublisher<CommentCreatedEvent> {
    private final Executor executor;
    private final BlockingQueue<CommentCreatedEvent> queue = new LinkedBlockingQueue<>();

    CommentCreatedEventPublisher(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void accept(FluxSink<CommentCreatedEvent> sink) {
        this.executor.execute(() -> {
            while (true)
                try {
                    CommentCreatedEvent event = queue.take();
                    System.out.println("Event in CommentCreatedEventPublisher ");
                    System.out.println(event);
                    sink.next(event);
                } catch (InterruptedException e) {
                    ReflectionUtils.rethrowRuntimeException(e);
                }
        });
    }

    @Override
    public void onApplicationEvent(CommentCreatedEvent event) {
        this.queue.offer(event);
    }
}

