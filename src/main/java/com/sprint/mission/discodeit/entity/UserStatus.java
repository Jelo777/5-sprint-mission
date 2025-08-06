package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;

public class UserStatus {
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
}
