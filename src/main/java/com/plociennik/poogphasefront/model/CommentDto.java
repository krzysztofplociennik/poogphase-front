package com.plociennik.poogphasefront.model;

import java.time.LocalDateTime;

public class CommentDto {
    private long id;
    private long authorId;
    private long postId;
    private LocalDateTime dateTime;
    private String content;

    public CommentDto(long id, long authorId, long postId, LocalDateTime dateTime, String content) {
        this.id = id;
        this.authorId = authorId;
        this.postId = postId;
        this.dateTime = dateTime;
        this.content = content;
    }

    public CommentDto() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
