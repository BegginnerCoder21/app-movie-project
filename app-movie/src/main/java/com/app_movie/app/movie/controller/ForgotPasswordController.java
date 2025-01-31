package com.app_movie.app.movie.controller;

import com.app_movie.app.movie.service.ForgotPasswordService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private ForgotPasswordService forgotPasswordService;

    @PostMapping("veriry-email/{email}")
    public ResponseEntity<String> verifyEmail(@PathVariable String email)
    {
        String result = this.forgotPasswordService.verifyEmail(email);

        return ResponseEntity.ok(result);
    }


}
