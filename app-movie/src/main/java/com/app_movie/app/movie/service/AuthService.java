package com.app_movie.app.movie.service;

import com.app_movie.app.movie.dto.AuthResponse;
import com.app_movie.app.movie.dto.LoginRequest;
import com.app_movie.app.movie.dto.RegisterRequest;

public interface AuthService {

    public AuthResponse register(RegisterRequest registerRequest);
    public AuthResponse login(LoginRequest loginRequest);
}
