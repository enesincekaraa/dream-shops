package com.enesincekara.dreamshops.security.auth.request;

public record RegisterRequest(
        String username,
        String password
) {
}
