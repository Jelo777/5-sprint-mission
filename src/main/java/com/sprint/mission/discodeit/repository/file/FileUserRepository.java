package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Repository
public class FileUserRepository implements UserRepository {
    private final String DIRECTORY;
    private final String EXTENSION = ".ser";

    public FileUserRepository() {
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
    public User save(User user) {
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
    public Optional<User> findById(UUID id) {
        Path path = Paths.get(DIRECTORY, id + EXTENSION);
        if (!path.toFile().exists()) {
            return Optional.empty();
        }
        try (FileInputStream fis = new FileInputStream(path.toFile());
             ObjectInputStream ois = new ObjectInputStream(fis);) {
            User user = (User) ois.readObject();
            return Optional.of(user);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
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

    @Override
    public boolean existsByUsername(String username) {
        try (Stream<Path> files = Files.list(Paths.get(DIRECTORY))) {
            return files.anyMatch(path -> {
                try (
                        FileInputStream fis = new FileInputStream(path.toFile());
                        ObjectInputStream ois = new ObjectInputStream(fis)
                ) {
                    User user = (User) ois.readObject();
                    return user.getUsername().equals(username);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        try (Stream<Path> files = Files.list(Paths.get(DIRECTORY))) {
            return files.anyMatch(path -> {
                try (
                        FileInputStream fis = new FileInputStream(path.toFile());
                        ObjectInputStream ois = new ObjectInputStream(fis)
                ) {
                    User user = (User) ois.readObject();
                    return user.getEmail().equals(email);
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Path directory = Paths.get(DIRECTORY);
        try (Stream<Path> stream = Files.list(directory)) {
            return stream
                    .filter(path -> path.toString().endsWith(EXTENSION))
                    .map(path -> {
                        try (
                                FileInputStream fis = new FileInputStream(path.toFile());
                                ObjectInputStream ois = new ObjectInputStream(fis)
                                ) {
                            return (User) ois.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            return null;
                        }
                    })
                    .filter(Objects::nonNull)
                    .filter(user -> user.getUsername().equals(username))
                    .findFirst();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
