package com.enesincekara.dreamshops.security.auth.response;

public record TokenResponse(
        String accessToken,
        String tokenType,
        String refreshToken
) {
}

