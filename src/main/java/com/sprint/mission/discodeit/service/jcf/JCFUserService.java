package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final Map<UUID, User> users =  new HashMap<>();

    public JCFUserService() {
        users.put(UUID.randomUUID(), new User("test1"));
        users.put(UUID.randomUUID(), new User("test2"));
        users.put(UUID.randomUUID(), new User("test3"));
        users.put(UUID.randomUUID(), new User("test4"));
        users.put(UUID.randomUUID(), new User("test5"));
    }

    @Override
    public boolean createUser(String nickName) {
        for(User existingUser : users.values()){
            if(existingUser.getNickname().equals(nickName)) {
                return false;
            }
        }
        User newUser = new User(nickName);
        users.put(newUser.getId(), newUser);
        return true;
    }

    @Override
    public boolean updateUser(String currentNickname, String newNickname) {
        for(User existingUser : users.values()){
            if(existingUser.getNickname().equals(currentNickname)) {
                existingUser.update(newNickname);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        for(User existingUser : users.values()){
            if(existingUser.getNickname().equals(user.getNickname())) {
                users.remove(existingUser.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserByNickname(String nickname) {
        for(User existingUser : users.values()){
            if(existingUser.getNickname().equals(nickname)) {
                return existingUser;
            }
        }
        return null;
    }


    @Override
    public Map<UUID, User> getUsers() {
        return users;
    }
}
