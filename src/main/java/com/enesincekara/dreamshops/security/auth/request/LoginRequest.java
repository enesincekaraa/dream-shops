package com.enesincekara.dreamshops.security.auth.request;

public record LoginRequest(
        String username,
        String password
) {
}
