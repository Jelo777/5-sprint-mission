package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ReadStatusDto;

import java.util.List;
import java.util.UUID;

public interface ReadStatusService {
    ReadStatusDto.Response create(ReadStatusDto.CreateRequest request);
    ReadStatusDto.Response update(ReadStatusDto.UpdateRequest request);
    ReadStatusDto.Response find(UUID id);
    List<ReadStatusDto.Response> findAllByUserId(UUID userId);
    void delete(UUID id);
}
