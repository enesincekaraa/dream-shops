package com.enesincekara.dreamshops.security.auth.user.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private boolean active;

    public  User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        roles.add(role.name());
        return roles;
    }
}
