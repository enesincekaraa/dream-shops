package com.enesincekara.dreamshops.security.filter;

import com.enesincekara.dreamshops.repository.AuditLogRepository;
import com.enesincekara.dreamshops.service.auditlog.AuditLogService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuditLogFilter extends OncePerRequestFilter {

    private final AuditLogService auditLogService;

    public AuditLogFilter(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        filterChain.doFilter(request, response);

        String username = "ANONYMOUS";
        String role = "NONE";

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            username = auth.getName();
            role = auth.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse("NONE");
        }

        auditLogService.log(
                username,
                role,
                request.getMethod(),
                request.getRequestURI(),
                response.getStatus(),
                request.getRemoteAddr()
        );
    }
}

