package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> messages = new HashMap<>();

    @Override
    public void sendMessage(String content, String sender, String chName) {
        Message message = new Message(content, sender, chName);
        messages.put(message.getId(), message);
    }

    @Override
    public Map<UUID, Message> getMessages(String chName) {
        Map<UUID, Message> chMessage = new HashMap<>();
        for (Message message : messages.values()) {
            if(message.getChName().equals(chName)){
                chMessage.put(message.getId(), message);
            }
        }
        return chMessage;
    }

    @Override
    public Map<UUID, Message> getMessageByNickname(String nickname) {
        Map<UUID, Message> nickMessage = new HashMap<>();
        for (Message message : messages.values()) {
            if(message.getChName().equals(nickname)){
                nickMessage.put(message.getId(), message);
            }
        }
        return nickMessage;
    }
}
