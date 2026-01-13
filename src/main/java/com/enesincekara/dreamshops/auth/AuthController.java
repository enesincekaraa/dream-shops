package com.enesincekara.dreamshops.auth;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtTokenService jwtTokenService;
    public AuthController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest req)
    {
        if (!req.username().equals("admin") || !req.password().equals("1234")) {
            return ResponseEntity.badRequest().build();
        }

        String token = jwtTokenService.generateToken(req.username()
                , List.of("ADMIN"));

        return ResponseEntity.ok(new TokenResponse(
                token,
                "Bearer"
        ));
    }
}
