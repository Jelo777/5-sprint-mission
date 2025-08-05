package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FileUserService implements UserService {
    private final String DIRECTORY;
    private final String EXTENSION = ".ser";

    public FileUserService() {
        this.DIRECTORY = "USER";
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
    public User create(String username, String email, String password) {
        User user = new User(username, email, password);
        Path path = Paths.get(DIRECTORY, user.getId() + EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public User find(UUID userId) {
        Path path = Paths.get(DIRECTORY, userId + EXTENSION);
        User user = null;
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            user = (User) ois.readObject();
        } catch (Exception e) {
            throw new NoSuchElementException("User not found");
        }
        return Optional.ofNullable(user).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Override
    public List<User> findAll() {
        Path directory = Paths.get(DIRECTORY);
        try {
            List<User> users = Files.list(directory)
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (User) data;
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toList();
            return users;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(UUID userId, String newUsername, String newEmail, String newPassword) {
        Path path = Paths.get(DIRECTORY, userId + EXTENSION);
        User userNullable = null;
        if (!path.toFile().exists()) {
            throw new NoSuchElementException("User not found");
        }
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            userNullable = (User) ois.readObject();
        } catch (Exception e) {
            throw new NoSuchElementException("User not found");
        }

        User user = Optional.ofNullable(userNullable).orElseThrow(() -> new NoSuchElementException("User not found"));
        user.update(newUsername, newEmail, newPassword);

        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)
        ) {
            oos.writeObject(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public void delete(UUID userId) {
        Path path = Paths.get(DIRECTORY, userId + EXTENSION);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
