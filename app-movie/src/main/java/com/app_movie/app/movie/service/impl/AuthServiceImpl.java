package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.AuthResponse;
import com.app_movie.app.movie.dto.RegisterRequest;
import com.app_movie.app.movie.entity.RefreshToken;
import com.app_movie.app.movie.entity.User;
import com.app_movie.app.movie.entity.UserRepository;
import com.app_movie.app.movie.entity.enumeration.UserRole;
import com.app_movie.app.movie.service.AuthService;
import com.app_movie.app.movie.service.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private JwtService jwtService;
    private RefreshTokenServiceImpl refreshTokenService;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, RefreshTokenServiceImpl refreshTokenService) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthResponse register(RegisterRequest registerRequest) {


        User user = User.builder()
                .email(registerRequest.getEmail())
                .role(UserRole.USER)
                .username(registerRequest.getUsername())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();

        User saveUser = this.userRepository.save(user);
        String accessToken = this.jwtService.generateToken(saveUser);
        RefreshToken refreshToken = this.refreshTokenService.creatingRefreshToken(saveUser.getEmail());


        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }
}
