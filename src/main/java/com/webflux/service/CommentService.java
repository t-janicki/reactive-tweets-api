package com.webflux.service;

import com.webflux.comment.event.CommentCreatedEvent;
import com.webflux.comment.model.Comment;
import com.webflux.comment.repository.CommentRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CommentService {
    private final ApplicationEventPublisher publisher;
    private final CommentRepository commentRepository;

    CommentService(ApplicationEventPublisher publisher, CommentRepository commentRepository) {
        this.publisher = publisher;
        this.commentRepository = commentRepository;
    }

    public Flux<Comment> all() {
        return commentRepository.findAll();
    }

    public Mono<Comment> get(String id) {
        return commentRepository.findById(id);
    }

    public Mono<Comment> create(String comment) {
        return commentRepository
                .save(new Comment(comment))
                .doOnSuccess(c -> this.publisher.publishEvent(new CommentCreatedEvent(c)));
    }

}
