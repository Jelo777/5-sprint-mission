package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.UUID;

public class ReadStatusDto {

    public record CreateRequest(
            UUID channelId,
            UUID userId
    ) {}

    public record Response(
            UUID id,
            UUID channelId,
            UUID userId,
            Instant updatedAt
    ) {}

    public record UpdateRequest(
            UUID id
    ) {}
}
