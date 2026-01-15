package com.enesincekara.dreamshops.auth.response;

public record TokenResponse(
        String accessToken,
        String tokenType,
        String refreshToken
) {
}

