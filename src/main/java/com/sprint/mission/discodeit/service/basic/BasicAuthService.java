package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BasicAuthService implements AuthService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UserDto.UserResponse login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->new NoSuchElementException("존재하지 않는 id"));
        if(!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
        boolean isOnline = userStatusRepository.findByUserId(user.getId())
                .map(UserStatus::isOnline)
                .orElse(false);
        return new UserDto.UserResponse(user.getId(), user.getUsername(), user.getEmail(), isOnline);
    }
}
