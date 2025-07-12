package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;

public class Message {
    private UUID id;
    private long createdAt;
    private long updatedAt;

    private String content;
    private String sender;
    private String chName;

    public Message() {
    }

    public Message(String content, String sender, String chName) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now().getEpochSecond();
        this.updatedAt = Instant.now().getEpochSecond();
        this.content = content;
        this.sender = sender;
        this.chName = chName;
    }

    public void update(String content, String sender, String chName) {
        this.content = content;
        this.updatedAt = Instant.now().getEpochSecond();
    }

    public UUID getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    public String getChName() {
        return chName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", content='").append(content).append('\'');
        sb.append(", sender='").append(sender).append('\'');
        sb.append(", chName='").append(chName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
