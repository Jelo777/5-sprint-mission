package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFMessageService implements MessageService {
    private final Map<UUID, Message> messages = new HashMap<>();

    public JCFMessageService() {
        messages.put(UUID.randomUUID(), new Message("테스트메시지1", "test1", "테스트채널1"));
        messages.put(UUID.randomUUID(), new Message("테스트메시지2", "test2", "테스트채널2"));
        messages.put(UUID.randomUUID(), new Message("테스트메시지3", "test3", "테스트채널3"));
        messages.put(UUID.randomUUID(), new Message("테스트메시지4", "test4", "테스트채널4"));
        messages.put(UUID.randomUUID(), new Message("테스트메시지5", "test5", "테스트채널5"));
    }

    @Override
    public Message sendMessage(String content, String sender, String chName) {
        UUID id = UUID.randomUUID();
        Message message = new Message(id, content, sender, chName);
        messages.put(message.getId(), message);
        return message;
    }

    @Override
    public Map<UUID, Message> getMessages() {
        return messages;
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

    @Override
    public Message getMessageById(UUID id) {
        return messages.get(id);
    }

    @Override
    public boolean deleteMessage(UUID id) {
        for(Message message : messages.values()){
            if(message.getId().equals(id)){
                messages.remove(message.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateMessage(UUID id, String content) {
        for (Message msg : messages.values()) {
            if(msg.getId().equals(id)){
                msg.update(content, msg.getSender(), msg.getChName());
                return true;
            }
        }
        return false;
    }
}
