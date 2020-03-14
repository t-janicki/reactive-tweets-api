package com.webflux.comment.controller;

import com.webflux.comment.model.Comment;
import com.webflux.comment.service.CommentService;
import org.reactivestreams.Publisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.net.URI;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping(value = "/comments")
    public Flux<Comment> getAllComments() {
        return commentService.all();
    }

    @GetMapping(value = "/stream/comments", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Comment> streamAllComments() {
        return commentService.all();
    }

    @PostMapping("/comments")
    public Publisher<ResponseEntity<Comment>> create(@RequestBody Comment comment) {
        return this.commentService
                .create(comment.getComment())
                .map(c -> ResponseEntity.created(URI.create("/comments"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .build());

    }
}
