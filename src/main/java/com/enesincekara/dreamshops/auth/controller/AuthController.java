package com.enesincekara.dreamshops.auth.controller;


import com.enesincekara.dreamshops.auth.request.LoginRequest;
import com.enesincekara.dreamshops.auth.request.RegisterRequest;
import com.enesincekara.dreamshops.auth.response.TokenResponse;
import com.enesincekara.dreamshops.auth.service.JwtTokenService;
import com.enesincekara.dreamshops.auth.user.model.Role;
import com.enesincekara.dreamshops.auth.user.model.User;
import com.enesincekara.dreamshops.auth.user.repository.UserRepository;
import com.enesincekara.dreamshops.config.PasswordConfig;
import com.enesincekara.dreamshops.exception.PasswordNotMatchedException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public AuthController(JwtTokenService jwtTokenService, UserRepository repository, PasswordEncoder passwordEncoder) {
        this.jwtTokenService = jwtTokenService;
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostMapping("/login")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody LoginRequest req) throws PasswordNotMatchedException {
        User user = repository.findByUsername(req.username()).orElseThrow(()-> new UsernameNotFoundException("Username not found"));

        if (!passwordEncoder.matches(req.password(), user.getPassword())){
            throw new PasswordNotMatchedException("Passwords don't match");
        }

        String token = jwtTokenService.generateToken(
                user.getUsername(),
                List.of(user.getRole().name())
        );
        return ResponseEntity.ok(new TokenResponse(
                token,
                "Bearer"
        ));
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest req){

        if (repository.findByUsername(req.username()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User user  = new User(
                req.username(),
                passwordEncoder.encode(req.password()),
                Role.USER
        );
        repository.save(user);
        return ResponseEntity.status(201).build();
    }
}
