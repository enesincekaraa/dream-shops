package com.enesincekara.dreamshops.auth;

public record TokenResponse(
            String accessToken,
            String tokenType
) {
}
