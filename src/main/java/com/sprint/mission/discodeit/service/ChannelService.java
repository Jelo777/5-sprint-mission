package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.dto.ChannelDto;

import java.util.List;
import java.util.UUID;

public interface ChannelService {
    ChannelDto.ChannelResponse createPublicChannel(ChannelDto.PublicChannelCreateRequest request);

    ChannelDto.ChannelResponse createPrivateChannel(ChannelDto.PrivateChannelCreateRequest request);

    ChannelDto.ChannelResponse find(UUID channelId);

    List<ChannelDto.ChannelResponse> findAllByUserId(UUID userId);

    ChannelDto.ChannelResponse update(ChannelDto.UpdateChannelRequest request);

    void delete(UUID channelId);
}
