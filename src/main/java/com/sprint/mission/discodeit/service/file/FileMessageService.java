package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileMessageService implements MessageService {
    private final String DIRECTORY;
    private final String EXTENSION = ".ser";

    private final UserService userService;
    private final ChannelService channelService;

    public FileMessageService(UserService userService, ChannelService channelService) {
        this.DIRECTORY = "MESSAGE";
        this.userService = userService;
        this.channelService = channelService;
        Path path = Paths.get(DIRECTORY);
        if (!path.toFile().exists()) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Message create(String content, UUID channelId, UUID authorId) {
        try {
            channelService.find(channelId);
            userService.find(authorId);
        } catch (NoSuchElementException e) {
            throw e;
        }
        Message message = new Message(content, channelId, authorId);
        Path path = Paths.get(DIRECTORY, message.getId() + EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public Message find(UUID messageId) {
        Path path = Paths.get(DIRECTORY, messageId + EXTENSION);
        Message message = null;
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            message = (Message) ois.readObject();
        } catch (Exception e) {
            throw new NoSuchElementException("Message not found");
        }
        return Optional.ofNullable(message).orElseThrow(() -> new NoSuchElementException("Message not found"));
    }

    @Override
    public List<Message> findAll() {
        Path directory = Paths.get(DIRECTORY);
        try {
            List<Message> messages = Files.list(directory)
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis);
                        ) {
                            Object data = ois.readObject();
                            return (Message) data;
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
            return messages;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message update(UUID messageId, String newContent) {
        Path path = Paths.get(DIRECTORY, messageId + EXTENSION);
        Message messageNullable = null;
        if (!path.toFile().exists()) {
            throw new NoSuchElementException("Message not found");
        }
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            messageNullable = (Message) ois.readObject();
        } catch (Exception e) {
            throw new NoSuchElementException("Message not found");
        }

        Message message = Optional.ofNullable(messageNullable).orElseThrow(() -> new NoSuchElementException("Message not found"));
        message.update(newContent);

        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public void delete(UUID messageId) {
        Path path = Paths.get(DIRECTORY, messageId + EXTENSION);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
