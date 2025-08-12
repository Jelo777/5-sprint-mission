package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileChannelService implements ChannelService {
    private final String DIRECTORY;
    private final String EXTENSION = ".ser";

    public FileChannelService() {
        this.DIRECTORY = "CHANNEL";
        Path path = Paths.get(DIRECTORY);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Channel create(ChannelType type, String name, String description) {
        Channel channel = new Channel(type, name, description);
        Path path = Paths.get(DIRECTORY, channel.getId() + EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(channel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return channel;
    }

    @Override
    public Channel find(UUID channelId) {
        Path path = Paths.get(DIRECTORY, channelId + EXTENSION);
        Channel channel = null;
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            channel = (Channel) ois.readObject();
        } catch (Exception e) {
            throw new NoSuchElementException("Channel not found");
        }
        return Optional.ofNullable(channel).orElseThrow(() -> new NoSuchElementException("Channel not found"));
    }

    @Override
    public List<Channel> findAll() {
        Path directory = Paths.get(DIRECTORY);
        try {
            List<Channel> channels = Files.list(directory)
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (Channel) data;
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
            return channels;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Channel update(UUID channelId, String newName, String newDescription) {
        Path path = Paths.get(DIRECTORY, channelId + EXTENSION);
        Channel channelNullable = null;
        if (!path.toFile().exists()) {
            throw new NoSuchElementException("Channel not found");
        }
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            channelNullable = (Channel) ois.readObject();
        } catch (Exception e) {
            throw new NoSuchElementException("User not found");
        }

        Channel channel = Optional.ofNullable(channelNullable).orElseThrow(() -> new NoSuchElementException("User not found"));
        channel.update(newName, newDescription);

        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(channel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return channel;
    }

    @Override
    public void delete(UUID channelId) {
        Path path = Paths.get(DIRECTORY, channelId + EXTENSION);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}