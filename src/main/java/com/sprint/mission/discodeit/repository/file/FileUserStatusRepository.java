package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static java.util.Arrays.stream;

@Repository
public class FileUserStatusRepository implements UserStatusRepository {
    private final String DIRECTORY;
    private final String EXTENSION = ".ser";

    public FileUserStatusRepository() {
        this.DIRECTORY = "USERSTATUS";
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
    public UserStatus save(UserStatus userStatus) {
        Path path = Paths.get(DIRECTORY, userStatus.getId() + EXTENSION);
        try (FileOutputStream fos = new FileOutputStream(path.toFile());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(userStatus);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userStatus;
    }

    @Override
    public Optional<UserStatus> findById(UUID id) {
        Path path = Paths.get(DIRECTORY, id + EXTENSION);
        if (!path.toFile().exists()) {
            return Optional.empty();
        }
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            UserStatus userStatus = (UserStatus) ois.readObject();
            return Optional.of(userStatus);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserStatus> findByUserId(UUID userId) {
        Path directory = Paths.get(DIRECTORY);
        try (Stream<Path> stream = Files.list(directory)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            return (UserStatus) ois.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(status -> status.getUserId().equals(userId))
                    .findFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserStatus> findAll() {
        Path directory = Paths.get(DIRECTORY);
        try {
            List<UserStatus> userStatuses = Files.list(directory)
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                        ) {
                            Object data = ois.readObject();
                            return (UserStatus) data;
                        } catch (IOException | ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }).toList();
            return userStatuses;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long count() {
        Path directory = Paths.get(DIRECTORY);
        try (Stream<Path> stream = Files.list(directory)) {
            return stream.filter(path -> path.toString().endsWith(EXTENSION)).count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(UUID id) {
        Path path = Paths.get(DIRECTORY, id + EXTENSION);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsById(UUID id) {
        Path path = Paths.get(DIRECTORY, id + EXTENSION);
        return Files.exists(path);
    }
}
