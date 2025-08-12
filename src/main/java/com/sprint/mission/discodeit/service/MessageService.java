package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.MessageDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {
    MessageDto.Response create(MessageDto.CreateRequest request);

    MessageDto.Response update(MessageDto.UpdateRequest request);

    List<MessageDto.Response> findAllByChannelId(UUID channelId);

    void delete(UUID messageId);
}
