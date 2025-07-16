package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCFChannelService implements ChannelService {
    private final Map<UUID, Channel> channels = new HashMap<>();

    public JCFChannelService() {
        channels.put(UUID.randomUUID(), new Channel("테스트채널1", "test1"));
        channels.put(UUID.randomUUID(), new Channel("테스트채널2", "test2"));
        channels.put(UUID.randomUUID(), new Channel("테스트채널3", "test3"));
        channels.put(UUID.randomUUID(), new Channel("테스트채널4", "test4"));
        channels.put(UUID.randomUUID(), new Channel("테스트채널5", "test5"));
    }

    @Override
    public boolean createChannel(String chName, String chOwn) {
        for (Channel c : channels.values()) {
            if (c.getChName().equals(chName)) {
                return false;
            }
        }
        Channel newChannel = new Channel(chName, chOwn);
        channels.put(newChannel.getId(), newChannel);
        return true;
    }

    @Override
    public boolean updateChannel(String chName, String newChName) {
        for (Channel c : channels.values()) {
            if (c.getChName().equals(chName)) {
                c.update(newChName);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteChannel(Channel channel) {
        for (Channel c : channels.values()) {
            if (c.getChName().equals(channel.getChName()) && c.getChOwn().equals(channel.getChOwn())) {
                channels.remove(c.getId());
                return true;
            }
        }
        return false;
    }

    @Override
    public Map<UUID, Channel> getChannel() {
        return channels;
    }

    @Override
//    public Map<UUID, Channel> getChannel(String chName) {
//        Map<UUID, Channel> searchCh = new HashMap<>();
//        for (Channel c : channels.values()) {
//            if(c.getChOwn().contains(chName)){
//                searchCh.put(c.getId(), c);
//            }
//        }
//        return searchCh;
//    }

    public Map<UUID, Channel> getChannel(String chName) {
        Map<UUID, Channel> searchCh = new HashMap<>();
        for (Channel c : channels.values()) {
            if(c.getChName().equals(chName)){
                searchCh.put(c.getId(), c);
            }
        }
        return searchCh;
    }

    @Override
    public Channel getChannelByName(String chName) {
        for(Channel c : channels.values()){
            if(c.getChName().equals(chName)){
                return c;
            }
        }
        return null;
    }
}
