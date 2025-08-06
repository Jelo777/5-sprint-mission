package com.sprint.mission.discodeit.entity;

import lombok.Getter;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Getter
public class User implements Serializable {
    private final UUID id;
    private UUID profileId;
    private final Instant createdAt;
    private Instant updatedAt;

    private String username;
    private String email;
    private String password;

    public User(String username, String email, String password) {
        this.id = UUID.randomUUID();                 // id 초기화
        this.createdAt = Instant.now();

        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void update(UUID profileId, String newUsername, String newEmail, String newPassword) {
        boolean anyValueUpdated = false;
        if(profileId != null || this.profileId == null){
            this.profileId = profileId;
            anyValueUpdated = true;
        }

        if (newUsername != null && !newUsername.equals(this.username)) {
            this.username = newUsername;
            anyValueUpdated = true;
        }
        if (newEmail != null && !newEmail.equals(this.email)) {
            this.email = newEmail;
            anyValueUpdated = true;
        }
        if (newPassword != null && !newPassword.equals(this.password)) {
            this.password = newPassword;
            anyValueUpdated = true;
        }
        if (anyValueUpdated) {
            this.updatedAt = Instant.now();
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", profileId=" + profileId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
