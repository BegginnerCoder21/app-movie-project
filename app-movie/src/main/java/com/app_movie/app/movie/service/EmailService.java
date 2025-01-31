package com.app_movie.app.movie.service;

import com.app_movie.app.movie.dto.MailBody;

public interface EmailService {

    public void sendMail(MailBody mailBody);
}
