package com.enesincekara.dreamshops.auth.user.model;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
public class RefreshToken {
    @Id
    private String id;

    @ManyToOne(optional = false)
    private User user;

    private Instant expiresAt;

    private RefreshToken(){}

    public RefreshToken(User user , long days){
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.expiresAt=Instant.now().plusSeconds(days*86400);
    }

    public boolean isExpired(){
        return Instant.now().isAfter(expiresAt);
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }
}
