package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.UserStatusDto;
import com.sprint.mission.discodeit.entity.UserStatus;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.repository.UserStatusRepository;
import com.sprint.mission.discodeit.service.UserStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicUserStatusService implements UserStatusService {
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public UserStatusDto.Response create(UserStatusDto.CreateRequest request) {
        userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("user not found"));
        userStatusRepository.findByUserId(request.userId())
                .ifPresent(existingUserStatus -> {
                    throw new IllegalStateException("UserStatus already exists");
                });
        UserStatus userStatus = new UserStatus(request.userId());
        userStatusRepository.save(userStatus);
        return new UserStatusDto.Response(
                userStatus.getId(), userStatus.getUserId(), userStatus.isOnline()
        );
    }

    @Override
    public UserStatusDto.Response update(UserStatusDto.UpdateRequest request) {
        UserStatus userStatus = userStatusRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("userStatus not found"));
        userStatus.update();
        return new UserStatusDto.Response(
                userStatus.getId(), userStatus.getUserId(), userStatus.isOnline()
        );
    }

    @Override
    public UserStatusDto.Response updateByUserId(UserStatusDto.UpdateByUserIdRequest request) {
        UserStatus userStatus = userStatusRepository.findByUserId(request.userId())
                .orElseThrow(() -> new RuntimeException("userStatus not found"));
        userStatus.update();
        return new UserStatusDto.Response(
                userStatus.getId(), userStatus.getUserId(), userStatus.isOnline()
        );
    }

    @Override
    public UserStatusDto.Response find(UUID id) {
        UserStatus userStatus =  userStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("userStatus not found"));
        return new UserStatusDto.Response(
                userStatus.getId(), userStatus.getUserId(), userStatus.isOnline()
        );
    }

    @Override
    public List<UserStatusDto.Response> findAll() {
        return userStatusRepository.findAll().stream()
                .map(userStatus -> new UserStatusDto.Response(
                        userStatus.getId(),
                        userStatus.getUserId(),
                        userStatus.isOnline()
                )).toList();
    }

    @Override
    public void delete(UUID id) {
        userStatusRepository.findById(id).orElseThrow(() -> new RuntimeException("userStatus not found"));
        userStatusRepository.delete(id);
    }
}
