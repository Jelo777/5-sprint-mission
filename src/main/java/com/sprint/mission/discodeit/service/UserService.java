package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserDto;

import java.util.List;
import java.util.UUID;

public interface UserService {
    UserDto.UserResponse create(UserDto.UserCreateRequest userCreateRequest);

    UserDto.UserResponse find(UUID userId);

    List<UserDto.UserResponse> findAll();

    UserDto.UserResponse update(UserDto.UserUpdateRequest userUpdateRequest);

    void delete(UUID userId);
}
