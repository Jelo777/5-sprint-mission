package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.entity.User;

import java.util.Map;
import java.util.UUID;

public interface ChannelService {
    boolean createChannel(String chName, String chOwn);
    boolean updateChannel(String chName, String chOwn);
    boolean deleteChannel(Channel channel);
    Map<UUID, Channel> getChannel();
    Map<UUID, Channel> getChannel(String chName);
    Channel getChannelByName(String chName);
}
