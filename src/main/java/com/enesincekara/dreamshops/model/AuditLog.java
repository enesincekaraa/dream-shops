package com.enesincekara.dreamshops.model;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String role;
    private String method;
    private String path;

    private int status;
    private String ip;

    private Instant timestamp;

    protected AuditLog() {

    }

    public AuditLog(String username, String role, String method, String path, int status, String ip) {
        this.username = username;
        this.role = role;
        this.method = method;
        this.path = path;
        this.status = status;
        this.ip = ip;
        this.timestamp = Instant.now();
    }
}
