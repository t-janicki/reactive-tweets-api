package com.webflux.tweet.model;

import com.webflux.comment.model.Comment;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "tweets")
@TypeAlias("tweets")
public class Tweet {
    @Id
    private String id;
    private String text;
    private Date createdAt = new Date();
    private List<Comment> comments;

    public Tweet() {
    }

    public Tweet(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public Tweet(String text) {
        this.text = text;
    }

    public Tweet(String text, List<Comment> comments) {
        this.text = text;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
