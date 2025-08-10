package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ReadStatusDto;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.ReadStatusRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicReadStatusService implements ReadStatusService {
    private final ReadStatusRepository readStatusRepository;
    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;

    @Override
    public ReadStatusDto.Response create(ReadStatusDto.CreateRequest request) {
        channelRepository.findById(request.channelId())
                .orElseThrow(() -> new RuntimeException("channel not found"));
        userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("user not found"));

        boolean alreadyExists = readStatusRepository.findAllUserIdsByChannelId(request.channelId())
                .stream().anyMatch(userId -> userId.equals(request.userId()));
        if(alreadyExists){
            throw new RuntimeException("이미 존재하는 ReadStatus 입니다.");
        }
        ReadStatus readStatus = new ReadStatus(request.userId(), request.channelId());
        readStatusRepository.save(readStatus);
        return new ReadStatusDto.Response(
                readStatus.getId(),
                readStatus.getChannelId(),
                readStatus.getUserId(),
                readStatus.getUpdatedAt()
        );
    }

    @Override
    public ReadStatusDto.Response update(ReadStatusDto.UpdateRequest request) {
        ReadStatus readStatus = readStatusRepository.findById(request.id())
                .orElseThrow(() -> new RuntimeException("read status not found"));
        readStatus.update();
        return new ReadStatusDto.Response(
                readStatus.getId(),
                readStatus.getChannelId(),
                readStatus.getUserId(),
                readStatus.getUpdatedAt()
        );
    }

    @Override
    public ReadStatusDto.Response find(UUID id) {
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("read status not found"));
        return new ReadStatusDto.Response(
                readStatus.getId(),
                readStatus.getChannelId(),
                readStatus.getUserId(),
                readStatus.getUpdatedAt()
        );
    }

    @Override
    public List<ReadStatusDto.Response> findAllByUserId(UUID userId) {
        return readStatusRepository.findAllByUserId(userId).stream()
                .map(readStatus -> new ReadStatusDto.Response(
                        readStatus.getId(),
                        readStatus.getChannelId(),
                        readStatus.getUserId(),
                        readStatus.getUpdatedAt()
                )).toList();
    }

    @Override
    public void delete(UUID id) {
        ReadStatus readStatus = readStatusRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("read status not found"));
        readStatusRepository.delete(readStatus.getId());
    }
}
