package com.sprint.mission.discodeit.dto;

import java.util.UUID;

public class UserDto {
    public record UserCreateRequest(
            String username,
            String email,
            String password,
            BinaryContentDto profileImage
    ) {
    }

    public record UserResponse(
            UUID id,
            String username,
            String email,
            boolean isOnline
    ) {
    }

    public record UserUpdateRequest(
            UUID id,
            String username,
            String password,
            String email,
            BinaryContentDto profileImage
    ) {
    }
}
