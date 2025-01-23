package com.app_movie.app.movie.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Table(name = "refresh_token")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Veuillez entrer le token svp")
    private String refreshToken;
    @NotBlank(message = "Veuillez entrer le temps d'expiration svp")
    private Instant expirationTime;
    @OneToOne
    private User user;
}
