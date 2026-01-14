package com.enesincekara.dreamshops.config;

import com.enesincekara.dreamshops.auth.user.model.Role;
import com.enesincekara.dreamshops.auth.user.model.User;
import com.enesincekara.dreamshops.auth.user.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordEncoder passwordEncoder1) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder1;
    }

    @PostConstruct
    void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            userRepository.save(
                    new User(
                            "admin",
                            passwordEncoder.encode("1234"),
                            Role.ADMIN
                    )
            );
        }
    }
}
