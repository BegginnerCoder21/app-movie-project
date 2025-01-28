package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.AuthResponse;
import com.app_movie.app.movie.dto.LoginRequest;
import com.app_movie.app.movie.dto.RegisterRequest;
import com.app_movie.app.movie.entity.RefreshToken;
import com.app_movie.app.movie.entity.User;
import com.app_movie.app.movie.entity.UserRepository;
import com.app_movie.app.movie.entity.enumeration.UserRole;
import com.app_movie.app.movie.service.AuthService;
import com.app_movie.app.movie.service.JwtService;
import com.app_movie.app.movie.service.RefreshTokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, RefreshTokenServiceImpl refreshTokenService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
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

    @Override
    public AuthResponse login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        User user = this.userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new UsernameNotFoundException("Utilisateur n'a pas été trouvé !"));
        String accessToken = this.jwtService.generateToken(user);
        RefreshToken refreshToken = this.refreshTokenService.creatingRefreshToken(loginRequest.getEmail());

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .build();
    }


}
