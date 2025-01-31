package com.app_movie.app.movie.service;

import com.app_movie.app.movie.dto.ChangePassword;
import org.springframework.http.ResponseEntity;

public interface ForgotPasswordService {

    public String verifyEmail(String email);
    public ResponseEntity<String> verifyOtp(String otp, String email);
    public ResponseEntity<String> changePassword(String email, ChangePassword changePassword);
}
