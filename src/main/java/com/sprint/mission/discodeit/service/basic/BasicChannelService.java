package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.ChannelDto;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.ChannelType;
import com.sprint.mission.discodeit.entity.ReadStatus;
import com.sprint.mission.discodeit.repository.*;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service("basicChannelService")
@RequiredArgsConstructor
public class BasicChannelService implements ChannelService {
    private final ChannelRepository channelRepository;
    private final MessageRepository messageRepository;
    private final ReadStatusRepository readStatusRepository;
    private final UserRepository userRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public ChannelDto.ChannelResponse createPublicChannel(ChannelDto.PublicChannelCreateRequest request) {
        Channel channel = new Channel(ChannelType.PUBLIC, request.name(), request.description());
        channelRepository.save(channel);
        return new ChannelDto.ChannelResponse(channel.getId(), channel.getType(), channel.getName(), channel.getDescription(),
                channel.getCreatedAt(), channel.getUpdatedAt(), null, List.of());
    }

    @Override
    public ChannelDto.ChannelResponse createPrivateChannel(ChannelDto.PrivateChannelCreateRequest request) {
        if (request.memberIds() == null || request.memberIds().isEmpty()) {
            throw new IllegalArgumentException("memberIds cannot be null or empty");
        }

        for (UUID memberId : request.memberIds()) {
            if (!userRepository.existsById(memberId)) {
                throw new NoSuchElementException("memberId not found : " + memberId);
            }
        }
        Channel channel = new Channel(ChannelType.PRIVATE, null, null);
        channelRepository.save(channel);
        for (UUID memberId : request.memberIds()) {
            ReadStatus readStatus = new ReadStatus(memberId, channel.getId());
            readStatusRepository.save(readStatus);
        }
        return new ChannelDto.ChannelResponse(channel.getId(), ChannelType.PRIVATE, null, null,
                channel.getCreatedAt(), channel.getUpdatedAt(), null, request.memberIds());
    }

    @Override
    public ChannelDto.ChannelResponse find(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("channel not found"));
        Instant lastestMessage = messageRepository.findLatestCreatedAtByChannelId(channelId).orElse(null);
        List<UUID> memberIds = null;
        if (channel.getType() == ChannelType.PRIVATE) {
            memberIds = readStatusRepository.findAllUserIdsByChannelId(channelId);
        }
        return new ChannelDto.ChannelResponse(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                lastestMessage,
                memberIds
        );
    }

    @Override
    public List<ChannelDto.ChannelResponse> findAllByUserId(UUID userId) {
        List<ChannelDto.ChannelResponse> publicChannels = channelRepository.findAllByType(ChannelType.PUBLIC)
                .stream()
                .map(channel -> new ChannelDto.ChannelResponse(
                        channel.getId(),
                        channel.getType(),
                        channel.getName(),
                        channel.getDescription(),
                        channel.getCreatedAt(),
                        channel.getUpdatedAt(),
                        messageRepository.findLatestCreatedAtByChannelId(channel.getId()).orElse(null),
                        List.of()
                )).toList();

        List<UUID> privateChannelIds = readStatusRepository.findAllChannelIdsByUserId(userId);

        List<ChannelDto.ChannelResponse> privateChannels = privateChannelIds.stream()
                .map(channelRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(channel -> channel.getType() == ChannelType.PRIVATE)
                .map(channel -> {
                    List<UUID> memberIds = readStatusRepository.findAllUserIdsByChannelId(channel.getId());
                    return new ChannelDto.ChannelResponse(
                            channel.getId(),
                            channel.getType(),
                            channel.getName(),
                            channel.getDescription(),
                            channel.getCreatedAt(),
                            channel.getUpdatedAt(),
                            messageRepository.findLatestCreatedAtByChannelId(channel.getId()).orElse(null),
                            memberIds
                    );
                }).toList();
        return Stream.concat(publicChannels.stream(), privateChannels.stream()).toList();
    }

    @Override
    public ChannelDto.ChannelResponse update(ChannelDto.UpdateChannelRequest request) {
        Channel channel = channelRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchElementException("channel not found"));
        if (channel.getType() == ChannelType.PRIVATE) {
            throw new IllegalArgumentException("PRIVATE 채널은 수정할 수 없습니다.");
        }
        channel.update(request.name(), request.description());
        channelRepository.save(channel);
        Instant lastestMessage = messageRepository.findLatestCreatedAtByChannelId(channel.getId()).orElse(null);
        return new ChannelDto.ChannelResponse(
                channel.getId(),
                channel.getType(),
                channel.getName(),
                channel.getDescription(),
                channel.getCreatedAt(),
                channel.getUpdatedAt(),
                lastestMessage,
                List.of()
        );
    }


    @Override
    public void delete(UUID channelId) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("channel not found"));
        messageRepository.findAllByChannelId(channel.getId()).forEach(message -> {
            if (message.getAttachmentIds() != null) {
                message.getAttachmentIds().forEach(binaryContentRepository::delete);
            }
            messageRepository.delete(message.getId());
        });
        readStatusRepository.findAllUserIdsByChannelId(channel.getId()).forEach(userRepository::delete);
        channelRepository.delete(channelId);
    }
}
