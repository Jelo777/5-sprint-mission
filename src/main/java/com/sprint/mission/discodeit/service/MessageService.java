package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.Map;
import java.util.UUID;

public interface MessageService {
    void sendMessage(String content, String sender, String chName);
    Map<UUID, Message> getMessages(String chName);
    Map<UUID, Message> getMessageByNickname(String nickname);
}
