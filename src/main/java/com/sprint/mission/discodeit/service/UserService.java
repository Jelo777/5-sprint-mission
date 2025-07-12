package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.Map;
import java.util.UUID;

public interface UserService {
    boolean createUser(String nickname);
    boolean updateUser(User user);
    boolean deleteUser(User user);
    User getUserByNickname(String nickname);
    Map<UUID, User> getUsers();
}
