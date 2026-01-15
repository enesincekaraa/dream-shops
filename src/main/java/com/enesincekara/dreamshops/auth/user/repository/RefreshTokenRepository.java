package com.enesincekara.dreamshops.auth.user.repository;


import com.enesincekara.dreamshops.auth.user.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
}
