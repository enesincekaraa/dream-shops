package com.enesincekara.dreamshops.security.filter;

import com.enesincekara.dreamshops.security.ratelimit.RateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class LoginRateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    public LoginRateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        if (isLoginRequest(request)) {
           String ip = request.getRemoteAddr();
           boolean allowed = rateLimitService.isAllowed(ip);

           if (!allowed) {
               response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
               response.setContentType("application/json");
               response.getWriter().write("""
                        {
                          "message": "Too many login attempts. Please try again later."
                        }
                        """);

               return;

           }
        }
        filterChain.doFilter(request, response);

    }


    private boolean isLoginRequest(HttpServletRequest request) {
        return "/api/auth/login".equals(request.getRequestURI())
                && "POST".equalsIgnoreCase(request.getMethod());
    }
}
