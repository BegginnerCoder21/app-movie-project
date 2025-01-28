package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.dto.AuthResponse;
import com.app_movie.app.movie.dto.LoginRequest;
import com.app_movie.app.movie.dto.RefreshTokenRequest;
import com.app_movie.app.movie.dto.RegisterRequest;
import com.app_movie.app.movie.entity.RefreshToken;
import com.app_movie.app.movie.entity.User;
import com.app_movie.app.movie.service.AuthService;
import com.app_movie.app.movie.service.JwtService;
import com.app_movie.app.movie.service.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    private final AuthService authService;
    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, RefreshTokenService refreshTokenService, JwtService jwtService) {
        this.authService = authService;
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest)
    {
        AuthResponse authResponse = this.authService.register(registerRequest);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest)
    {
        AuthResponse authResponse = this.authService.login(loginRequest);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest)
    {
        RefreshToken refreshToken = this.refreshTokenService.verifyRefreshToken(refreshTokenRequest.getRefreshToken());
        User user = refreshToken.getUser();
        String accessToken = this.jwtService.generateToken(user);

        AuthResponse authResponse = AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();

        return ResponseEntity.ok(authResponse);
    }
}
