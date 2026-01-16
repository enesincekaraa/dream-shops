package com.enesincekara.dreamshops.security.auth.user.repository;


import com.enesincekara.dreamshops.security.auth.user.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
