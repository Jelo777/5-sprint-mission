package com.sprint.mission.discodeit.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class MessageDto {
    public record CreateRequest(
            String content,
            UUID channelId,
            UUID authorId,
            List<BinaryContentDto> attachments
    ) {
    }

    public record UpdateRequest(
            UUID id,
            String content
    ) {
    }

    public record Response(
            UUID id,
            String content,
            UUID channelId,
            UUID authorId,
            Instant createdAt,
            Instant updatedAt,
            List<UUID> attachmentIds
    ) {
    }
}
