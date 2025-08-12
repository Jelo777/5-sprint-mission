package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Getter
public class UserStatus implements Serializable {
    private final UUID id;
    private final Instant createdAt;
    private Instant updatedAt;
    private final UUID userId;

    public UserStatus(UUID userId) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.userId = userId;
    }

    public void update() {
        this.updatedAt = Instant.now();
    }

    public boolean isOnline() {
        Instant now = Instant.now();
        return Duration.between(updatedAt, now).toMinutes() < 5;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                '}';
    }
}
