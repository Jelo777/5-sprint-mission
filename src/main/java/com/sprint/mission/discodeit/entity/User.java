package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;

public class User {
    private UUID id;
    private long createdAt;
    private long updatedAt;

    private String nickname;

    public User(String nickname) {
        this.id = UUID.randomUUID();                 // id 초기화
        this.createdAt = Instant.now().getEpochSecond();
        this.nickname = nickname;
    }

    public User() {
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

    public String getNickname() {
        return nickname;
    }

    public void update(String nickname) {
        this.nickname = nickname;
        this.updatedAt = Instant.now().getEpochSecond();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", nickname='").append(nickname).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
