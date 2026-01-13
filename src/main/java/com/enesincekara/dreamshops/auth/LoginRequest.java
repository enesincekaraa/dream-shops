package com.enesincekara.dreamshops.auth;

public record LoginRequest(
        String username,
        String password
) {
}
