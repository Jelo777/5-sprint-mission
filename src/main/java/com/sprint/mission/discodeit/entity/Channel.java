package com.sprint.mission.discodeit.entity;

import java.time.Instant;
import java.util.UUID;

public class Channel {
    private UUID id;
    private long createdAt;
    private long updatedAt;

    private String chName;
    private String chOwn;

    public Channel() {
    }

    public Channel(String chName, String chOwn){
        this.id = UUID.randomUUID();
        this.chName = chName;
        this.chOwn = chOwn;
        this.createdAt = Instant.now().getEpochSecond();
        this.updatedAt = Instant.now().getEpochSecond();
    }

    public void setChOwn(String chOwn) {
        this.chOwn = chOwn;
    }

    public void setChName(String chName){
        this.chName = chName;
    }

    public void update(String chName){
        this.chName = chName;
        this.updatedAt = Instant.now().getEpochSecond();
    }

    public UUID getId() {
        return id;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public String getChName() {
        return chName;
    }

    public String getChOwn() {
        return chOwn;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Channel{");
        sb.append("id=").append(id);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", chName='").append(chName).append('\'');
        sb.append(", chOwn='").append(chOwn).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
