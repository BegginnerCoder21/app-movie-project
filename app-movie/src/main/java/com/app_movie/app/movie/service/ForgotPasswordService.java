package com.app_movie.app.movie.service;

public interface ForgotPasswordService {

    public String verifyEmail(String email);
    public String verifyOtp(String otp, String email);
}
