package com.sprint.mission.discodeit.dto;

import com.sprint.mission.discodeit.entity.BinaryContent;

public record BinaryContentDto(
        String name,
        String type,
        byte[] data
) {
    public BinaryContent toEntity() {
        return new BinaryContent(name, type, data);
    }
}
