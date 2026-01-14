package com.enesincekara.dreamshops.auth.request;

public record LoginRequest(
        String username,
        String password
) {
}
