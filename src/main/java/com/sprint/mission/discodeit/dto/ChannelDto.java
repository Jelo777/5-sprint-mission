package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.ChannelType;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ChannelDto {
    public record ChannelResponse(
            UUID id,
            ChannelType type,
            String name,
            String description,
            Instant createAt,
            Instant updateAt,
            Instant latestMessageAt,
            List<UUID> memberIds
    ) {
    }

    public record PublicChannelCreateRequest(
            String name,
            String description
    ) {
    }

    public record PrivateChannelCreateRequest(
            List<UUID> memberIds
    ) {
    }

    public record FindChannelsByUserRequest(
            UUID userId
    ) {
    }

    public record UpdateChannelRequest(
            UUID id,
            String name,
            String description
    ) {
    }
}
