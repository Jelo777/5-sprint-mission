package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.Map;
import java.util.UUID;

public interface MessageService {
    Message sendMessage(String content, String sender, String chName);
    Map<UUID, Message> getMessages();
    Map<UUID, Message> getMessages(String chName);
    Map<UUID, Message> getMessageByNickname(String nickname);
    Message getMessageById(UUID id);
    boolean deleteMessage(UUID id);
    boolean updateMessage(UUID id, String content);
}
