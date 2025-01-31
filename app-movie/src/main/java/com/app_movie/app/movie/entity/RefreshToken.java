package com.app_movie.app.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@Table(name = "refresh_token")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Veuillez entrer le token svp")
    private String refreshToken;
    @NotNull(message = "Veuillez entrer le temps d'expiration svp")
    private Instant expirationTime;
    @OneToOne
    private User user;
}
