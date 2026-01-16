package com.enesincekara.dreamshops.service.auditlog;

import com.enesincekara.dreamshops.model.AuditLog;
import com.enesincekara.dreamshops.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

@Service
public class AuditLogService {

    private final AuditLogRepository repository;

    public AuditLogService(AuditLogRepository repository) {
        this.repository = repository;
    }

    public void log(
            String username,
            String role,
            String method,
            String path,
            int status,
            String ip
    ) {
        AuditLog log = new AuditLog(
                username,
                role,
                method,
                path,
                status,
                ip
        );

        repository.save(log);
    }
}

