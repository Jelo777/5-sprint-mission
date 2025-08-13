package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.dto.data.ChannelDto;
import com.sprint.mission.discodeit.dto.request.PrivateChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelCreateRequest;
import com.sprint.mission.discodeit.dto.request.PublicChannelUpdateRequest;
import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/channel")
public class ChannelController {
    private final ChannelService channelService;

    @RequestMapping(path = "create/public", method = RequestMethod.POST)
    public ResponseEntity<Channel> createPublicChannel(
            @RequestBody PublicChannelCreateRequest channelCreateRequest) {
        Channel channel = channelService.create(channelCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(channel);
    }

    @RequestMapping(path = "create/private", method = RequestMethod.POST)
    public ResponseEntity<Channel> createPrivateChannel(
            @RequestBody PrivateChannelCreateRequest channelCreateRequest) {
        Channel channel = channelService.create(channelCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(channel);
    }

    @RequestMapping(path = "update/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Channel> updateChannel(
            @PathVariable UUID id,
            @RequestBody PublicChannelUpdateRequest channelUpdateRequest) {
        Channel channel = channelService.update(id, channelUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(channel);
    }

    @RequestMapping(path = "delete/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteChannel(@PathVariable UUID id) {
        channelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @RequestMapping(path = "findAll/{id}", method = RequestMethod.GET)
    public ResponseEntity<List<ChannelDto>> findAllChannels(@PathVariable UUID id) {
        List<ChannelDto> channelDtoList = channelService.findAllByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(channelDtoList);
    }
}
