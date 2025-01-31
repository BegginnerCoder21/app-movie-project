package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.MailBody;
import com.app_movie.app.movie.entity.ForgotPassword;
import com.app_movie.app.movie.entity.User;
import com.app_movie.app.movie.entity.UserRepository;
import com.app_movie.app.movie.repository.ForgotPasswordRepository;
import com.app_movie.app.movie.service.EmailService;
import com.app_movie.app.movie.service.ForgotPasswordService;
import com.app_movie.app.movie.utils.AuthUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;

    public ForgotPasswordServiceImpl(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
    }

    @Override
    @Transactional
    public String verifyEmail(String email) {

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("L'utilisateur avec ce email n'a pas été trouvé"));

        Integer otp = AuthUtils.generateOtp();

        MailBody mailBody = MailBody.builder()
                .to(user.getEmail())
                .subject("OTP pour l'oublie de votre mot de passe !")
                .message(String.format("Ce code OTP %s vous a été envoyé afin de procéder à la réinitialisation de votre mot de passe.", otp))
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .expirationTime(new Date(System.currentTimeMillis() + 70*1000))
                .user(user)
                .build();

        this.forgotPasswordRepository.save(forgotPassword);

        this.emailService.sendMail(mailBody);

        return "L'otp de rénitialisation a été envoyé !";
    }
}
