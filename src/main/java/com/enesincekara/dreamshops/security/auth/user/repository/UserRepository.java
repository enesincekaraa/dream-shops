package com.enesincekara.dreamshops.security.auth.user.repository;

import com.enesincekara.dreamshops.security.auth.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
