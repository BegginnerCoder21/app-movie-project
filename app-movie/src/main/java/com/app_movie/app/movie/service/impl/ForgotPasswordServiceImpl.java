package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.ChangePassword;
import com.app_movie.app.movie.dto.MailBody;
import com.app_movie.app.movie.entity.ForgotPassword;
import com.app_movie.app.movie.entity.User;
import com.app_movie.app.movie.repository.UserRepository;
import com.app_movie.app.movie.repository.ForgotPasswordRepository;
import com.app_movie.app.movie.service.EmailService;
import com.app_movie.app.movie.service.ForgotPasswordService;
import com.app_movie.app.movie.utils.AuthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final ForgotPasswordRepository forgotPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public ForgotPasswordServiceImpl(UserRepository userRepository, EmailService emailService, ForgotPasswordRepository forgotPasswordRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.forgotPasswordRepository = forgotPasswordRepository;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public ResponseEntity<String> verifyOtp(String otp, String email) {

        User user = this.userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("L'utilisateur avec ce email n'a pas été trouvé !"));

        ForgotPassword forgotPassword = this.forgotPasswordRepository.findByOtpAndUser(otp, user).orElseThrow(() -> new RuntimeException("L'otp fourni ne correspond pas à ce utilisateur !"));

        if(forgotPassword.getExpirationTime().before(Date.from(Instant.now())))
        {
            log.warn("L'otp a expiré");
            return new ResponseEntity<>("L'otp que vous avez fourni a expiré !",HttpStatus.EXPECTATION_FAILED);
        }

        return ResponseEntity.ok("OTP verifié avec succès");
    }

    @Override
    public ResponseEntity<String> changePassword(String email, ChangePassword changePassword) {

        if(!Objects.equals(changePassword.password(), changePassword.repeatPassword()))
        {
            return new ResponseEntity<>("Les deux mot de passe sont differents", HttpStatus.EXPECTATION_FAILED);
        }

        String encodedPassword = this.passwordEncoder.encode(changePassword.password());
        this.userRepository.updateUserPassword(email, encodedPassword);
        
        return ResponseEntity.ok("Votre mot de passe a bien été changé !");
    }
}
