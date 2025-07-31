package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.util.List;
import java.util.UUID;

public class FileUserService implements UserService {

    @Override
    public User create(String username, String email, String password) {
        return null;
    }

    @Override
    public User find(UUID userId) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User update(UUID userId, String newUsername, String newEmail, String newPassword) {
        return null;
    }

    @Override
    public void delete(UUID userId) {

    }
}
