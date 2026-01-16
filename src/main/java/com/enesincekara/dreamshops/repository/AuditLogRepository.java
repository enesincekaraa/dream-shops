package com.enesincekara.dreamshops.repository;

import com.enesincekara.dreamshops.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

}
