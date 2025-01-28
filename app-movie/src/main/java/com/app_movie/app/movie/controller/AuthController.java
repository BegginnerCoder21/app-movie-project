package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.dto.AuthResponse;
import com.app_movie.app.movie.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest registerRequest)
    {

    }
}
