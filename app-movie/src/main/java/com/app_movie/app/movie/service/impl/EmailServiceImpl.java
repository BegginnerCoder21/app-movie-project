package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.dto.MailBody;
import com.app_movie.app.movie.service.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    @Value("${mail.username}")
    private String emailFrom;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(MailBody mailBody) {

        SimpleMailMessage contentMail = new SimpleMailMessage();
        contentMail.setFrom(this.emailFrom);
        contentMail.setTo(mailBody.to());
        contentMail.setSubject(mailBody.subject());
        contentMail.setText(mailBody.message());

        this.javaMailSender.send(contentMail);
    }
}
