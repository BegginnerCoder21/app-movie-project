package com.app_movie.app.movie.dto;

import lombok.Builder;

@Builder
public record MailBody(String to, String subject, String message) {
}
