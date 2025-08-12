package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import java.util.UUID;

@Getter
public class BinaryContent implements Serializable {
    private final UUID id;
    private final Instant createdAt;

    private final String name;
    private final String contentType;
    private final byte[] data;

    public BinaryContent(String name, String contentType, byte[] data) {
        this.id = UUID.randomUUID();
        this.createdAt = Instant.now();
        this.name = name;
        this.contentType = contentType;
        this.data = data;
    }

    @Override
    public String toString() {
        return "BinaryContent{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", contentType='" + contentType + '\'' +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
