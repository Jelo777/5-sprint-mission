package com.sprint.mission.discodeit.service.basic;

import com.sprint.mission.discodeit.dto.BinaryContentDto;
import com.sprint.mission.discodeit.dto.BinaryContentResponseDto;
import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.repository.BinaryContentRepository;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BasicBinaryContentService implements BinaryContentService {
    private final BinaryContentRepository binaryContentRepository;

    @Override
    public BinaryContentResponseDto create(BinaryContentDto request) {
        BinaryContent binaryContent = new BinaryContent(request.name(), request.type(), request.data());
        binaryContentRepository.save(binaryContent);
        return new BinaryContentResponseDto(
                binaryContent.getId(),
                binaryContent.getName(),
                binaryContent.getContentType(),
                binaryContent.getCreatedAt()
        );
    }

    @Override
    public BinaryContentResponseDto find(UUID id) {
        BinaryContent binaryContent = binaryContentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("BinaryContent not found"));
        return new BinaryContentResponseDto(
                binaryContent.getId(),
                binaryContent.getName(),
                binaryContent.getContentType(),
                binaryContent.getCreatedAt()
        );
    }

    @Override
    public List<BinaryContentResponseDto> findAllByIdIn(List<UUID> ids) {
        List<BinaryContentResponseDto> result = new ArrayList<>();
        for (UUID id : ids) {
            binaryContentRepository.findById(id)
                    .ifPresent(binaryContent ->
                            result.add(new BinaryContentResponseDto(
                                    binaryContent.getId(),
                                    binaryContent.getName(),
                                    binaryContent.getContentType(),
                                    binaryContent.getCreatedAt()
                            )));
        }
        return result;
    }

    @Override
    public void delete(UUID id) {
        binaryContentRepository.findById(id).orElseThrow(() -> new NoSuchElementException("BinaryContent not found"));
        binaryContentRepository.delete(id);
    }
}
