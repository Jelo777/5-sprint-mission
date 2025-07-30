package com.sprint.mission.discodeit.repository.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.repository.UserRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FileUserRepository implements UserRepository {
    private final String DIRECTORY;
    private final String EXTENSION;

    public FileUserRepository(String directory, String extension) {
        this.DIRECTORY = "USER";
        this.EXTENSION = "ser";
        Path path = Paths.get(DIRECTORY);
        if(!path.toFile().exists()){
        }
    }

    @Override
    public User save(User user) {
        Path path = Paths.get(DIRECTORY, user.getId() + "." + EXTENSION);
        return null;
    }

    @Override
    public Optional<User> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public User delete(UUID id) {
        return null;
    }

    @Override
    public boolean existsById(UUID id) {
        return false;
    }
}
