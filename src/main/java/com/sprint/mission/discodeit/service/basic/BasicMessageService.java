package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.MessageDto;
import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.repository.ChannelRepository;
import com.sprint.mission.discodeit.repository.MessageRepository;
import com.sprint.mission.discodeit.repository.UserRepository;
import com.sprint.mission.discodeit.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service("basicMessageService")
@RequiredArgsConstructor
public class BasicMessageService implements MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChannelRepository channelRepository;
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public MessageDto.Response create(MessageDto.CreateRequest request) {
        channelRepository.findById(request.channelId())
                .orElseThrow(() -> new NoSuchElementException("Channel not found"));
        userRepository.findById(request.authorId())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        List<UUID> attachmentIds = Optional.ofNullable(request.attachments())
                .orElse(List.of())
                .stream()
                .map(binaryContentDto -> binaryContentRepository.save(binaryContentDto.toEntity()).getId())
                .toList();

        Message message = new Message(request.content(), request.channelId(), request.authorId(), attachmentIds);
        messageRepository.save(message);

        return new MessageDto.Response(
                message.getId(),
                message.getContent(),
                message.getChannelId(),
                message.getAuthorId(),
                message.getCreatedAt(),
                message.getUpdatedAt(),
                message.getAttachmentIds()
        );
    }

    @Override
    public MessageDto.Response update(MessageDto.UpdateRequest request) {
        Message message = messageRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchElementException("Message not found"));
        message.update(request.content());
        messageRepository.save(message);
        return new MessageDto.Response(
                message.getId(),
                message.getContent(),
                message.getChannelId(),
                message.getAuthorId(),
                message.getCreatedAt(),
                message.getUpdatedAt(),
                message.getAttachmentIds()
        );
    }

    @Override
    public List<MessageDto.Response> findAllByChannelId(UUID channelId) {
        channelRepository.findById(channelId)
                .orElseThrow(() -> new NoSuchElementException("Channel not found"));
        return messageRepository.findAllByChannelId(channelId).stream()
                .map(message -> new MessageDto.Response(
                        message.getId(),
                        message.getContent(),
                        message.getChannelId(),
                        message.getAuthorId(),
                        message.getCreatedAt(),
                        message.getUpdatedAt(),
                        message.getAttachmentIds()
                )).toList();
    }

    @Override
    public void delete(UUID messageId) {
        messageRepository.findById(messageId).ifPresent(message -> {
            Optional.ofNullable(message.getAttachmentIds())
                    .orElse(List.of())
                    .forEach(binaryContentRepository::delete);
            messageRepository.delete(message.getId());
        });
    }
}
