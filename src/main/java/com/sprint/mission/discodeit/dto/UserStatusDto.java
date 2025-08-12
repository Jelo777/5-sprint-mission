package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public class UserStatusDto {
    public record CreateRequest(
            UUID userId
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            boolean isOnline
    ) {
    }

    public record UpdateRequest(
            UUID id
    ) {
    }

    public record UpdateByUserIdRequest(
            UUID userId
    ) {
    }
}
