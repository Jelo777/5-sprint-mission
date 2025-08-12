package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service("basicUserService")
@RequiredArgsConstructor
public class BasicUserService implements UserService {
    private final UserRepository userRepository;
    private final UserStatusRepository userStatusRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public UserDto.UserResponse create(UserDto.UserCreateRequest userCreateRequest) {
        if (userRepository.existsByUsername(userCreateRequest.username())) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
        if (userRepository.existsByEmail(userCreateRequest.email())) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
        UUID profileId = null;
        if (userCreateRequest.profileImage() != null) {
            BinaryContent binaryContent =
                    binaryContentRepository.save(userCreateRequest.profileImage().toEntity());
            profileId = binaryContent.getId();
        }

        User user = new User(userCreateRequest.username(),
                userCreateRequest.email(),
                userCreateRequest.password(),
                profileId
        );

        userRepository.save(user);
        UserStatus userStatus = new UserStatus(user.getId());
        userStatusRepository.save(userStatus);
        return new UserDto.UserResponse(user.getId(), user.getUsername(), user.getEmail(), userStatus.isOnline());
    }


    @Override
    public UserDto.UserResponse find(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        UserStatus userStatus = userStatusRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
        return new UserDto.UserResponse(user.getId(), user.getUsername(), user.getEmail(), userStatus.isOnline());
    }

    @Override
    public List<UserDto.UserResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> {
            boolean isOnline = userStatusRepository.findByUserId(user.getId())
                    .map(UserStatus::isOnline).orElse(false);
            return new UserDto.UserResponse(user.getId(),
                    user.getUsername(),
                    user.getEmail(),
                    isOnline
            );
        }).toList();
    }

    @Override
    public UserDto.UserResponse update(UserDto.UserUpdateRequest userUpdateRequest) {
        User user = userRepository.findById(userUpdateRequest.id())
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (userRepository.existsByUsername(userUpdateRequest.username())) {
            throw new IllegalArgumentException("이미 존재하는 아이디.");
        }
        if (userRepository.existsByEmail(userUpdateRequest.email())) {
            throw new IllegalArgumentException("이미 존재하는 이메일.");
        }
        UUID profileId = user.getProfileId();
        if (userUpdateRequest.profileImage() != null) {
            BinaryContent binaryContent =
                    binaryContentRepository.save(userUpdateRequest.profileImage().toEntity());
        }
        user.update(profileId, userUpdateRequest.username(), userUpdateRequest.email(), userUpdateRequest.password());
        userRepository.save(user);
        UserStatus userStatus = userStatusRepository.findByUserId(user.getId())
                .orElseThrow(() -> new NoSuchElementException("UserStatus not found"));
        return new UserDto.UserResponse(user.getId(), user.getUsername(), user.getEmail(), userStatus.isOnline());
    }


    @Override
    public void delete(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        if (user.getProfileId() != null) {
            binaryContentRepository.delete(user.getProfileId());
        }
        userStatusRepository.findByUserId(userId)
                        .ifPresent(userStatus -> userStatusRepository.delete(userStatus.getId()));
        userRepository.delete(userId);
    }
}
