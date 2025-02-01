package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.MailBody;
import com.app_movie.app.movie.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailFrom;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(MailBody mailBody) {

        log.info("email from: {}", this.emailFrom);
        SimpleMailMessage contentMail = new SimpleMailMessage();
        contentMail.setFrom(this.emailFrom);
        contentMail.setTo(mailBody.to());
        contentMail.setSubject(mailBody.subject());
        contentMail.setText(mailBody.message());

        this.javaMailSender.send(contentMail);
    }
}
