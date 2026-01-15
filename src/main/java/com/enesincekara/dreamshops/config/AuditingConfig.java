package com.enesincekara.dreamshops.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AuditingConfig implements AuditorAware<String> {


    @Override
    public Optional<String> getCurrentAuditor() {
        return SecurityContextHolder.getContext()
                .getAuthentication() == null
                ? Optional.of("system")
                : Optional.of(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
