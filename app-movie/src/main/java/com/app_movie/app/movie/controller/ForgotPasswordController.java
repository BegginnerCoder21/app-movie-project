package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.dto.ChangePassword;
import com.app_movie.app.movie.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    public ForgotPasswordController(ForgotPasswordService forgotPasswordService) {
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("verify-email/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email)
    {
        String result = this.forgotPasswordService.verifyEmail(email);

        return ResponseEntity.ok(result);
    }

    @PostMapping("verify-otp/{otp}/{email}")
    public ResponseEntity<String> verifyOtp(@PathVariable String otp, @PathVariable String email)
    {
        return this.forgotPasswordService.verifyOtp(otp, email);

    }

    @PostMapping("/change-password/{email}")
    public ResponseEntity<String> changePassword(@PathVariable String email, @RequestBody ChangePassword changePassword)
    {
        return this.forgotPasswordService.changePassword(email, changePassword);
    }


}
