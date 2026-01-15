package com.enesincekara.dreamshops.auth.user.service;

import com.enesincekara.dreamshops.auth.user.model.RefreshToken;
import com.enesincekara.dreamshops.auth.user.model.User;
import com.enesincekara.dreamshops.auth.user.repository.RefreshTokenRepository;
import com.enesincekara.dreamshops.auth.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public RefreshToken create(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(username)
        );
        RefreshToken token = new RefreshToken(user,7);
        return refreshTokenRepository.save(token);
    }

    public RefreshToken validate(String tokenId) {
        RefreshToken token = refreshTokenRepository.findById(tokenId).orElseThrow
                (
                        ()-> new RuntimeException("")
                );

        if (token.isExpired()) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh Token expired");
        }
        return token;
    }

    public void revoke(String tokenId) {
        refreshTokenRepository.findById(tokenId).ifPresent(refreshTokenRepository::delete);
    }
}
