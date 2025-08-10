package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.UserStatusDto;

import java.util.List;
import java.util.UUID;

public interface UserStatusService {
    UserStatusDto.Response create(UserStatusDto.CreateRequest request);
    UserStatusDto.Response update(UserStatusDto.UpdateRequest request);
    UserStatusDto.Response updateByUserId(UserStatusDto.UpdateByUserIdRequest request);
    UserStatusDto.Response find(UUID id);
    List<UserStatusDto.Response> findAll();
    void delete(UUID id);
}
