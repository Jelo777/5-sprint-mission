package com.sprint.mission.discodeit.repository.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.repository.ChannelRepository;

import java.util.*;

public class JCFChannelRepository implements ChannelRepository {
    private final Map<UUID, Channel> data;

    public JCFChannelRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public Channel save(Channel channel) {
        data.put(channel.getId(), channel);
        return channel;
    }

    @Override
    public Optional<Channel> findById(UUID id) {
        Channel channel = data.get(id);
        return Optional.ofNullable(channel);
    }

    @Override
    public List<Channel> findAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public long count() {
        return this.data.size();
    }

    @Override
    public void delete(UUID id) {
        data.remove(id);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.data.containsKey(id);
    }
}
