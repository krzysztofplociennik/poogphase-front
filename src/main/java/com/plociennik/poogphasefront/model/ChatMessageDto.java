package com.plociennik.poogphasefront.model;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private long id;
    private long authorId;
    private String recipient;
    private LocalDateTime dateTime;
    private String content;

    public ChatMessageDto(long id, long authorId, String recipient, LocalDateTime dateTime, String content) {
        this.id = id;
        this.authorId = authorId;
        this.recipient = recipient;
        this.dateTime = dateTime;
        this.content = content;
    }

    public ChatMessageDto() {
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
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
