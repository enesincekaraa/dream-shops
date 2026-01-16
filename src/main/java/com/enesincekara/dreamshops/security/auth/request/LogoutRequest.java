package com.enesincekara.dreamshops.security.auth.request;

public record LogoutRequest(
        String refreshToken
) {
}
