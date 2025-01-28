package com.app_movie.app.movie.service.impl;

import com.app_movie.app.movie.entity.RefreshToken;
import com.app_movie.app.movie.repository.RefreshTokenRepository;
import com.app_movie.app.movie.entity.User;
import com.app_movie.app.movie.entity.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl {


    private final UserRepository userRepository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenServiceImpl(UserRepository userRepository, RefreshTokenRepository refreshTokenRepository) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken creatingRefreshToken(String username)
    {
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur n'a été trouvé"));

        RefreshToken refreshToken = user.getRefreshToken();

        if(refreshToken == null)
        {
            long expirationRefreshToken = 30L *24 *60 *60 *1000;

            return RefreshToken.builder()
                    .refreshToken(UUID.randomUUID().toString())
                    .expirationTime(Instant.now().plusMillis(expirationRefreshToken))
                    .user(user)
                    .build();
        }

        return refreshToken;

    }

    public RefreshToken verifyRefreshToken(String refreshToken)
    {
        RefreshToken refreshTok = this.refreshTokenRepository.findByRefreshToken(refreshToken).orElseThrow(() -> new RuntimeException("Aucun refresh token n'a été trouvé !"));

        if(refreshTok.getExpirationTime().isBefore(Instant.now()))
        {
            this.refreshTokenRepository.delete(refreshTok);
            throw new RuntimeException("Le refresh token que vous avez fourni a expiré !");
        }

        return refreshTok;
    }
}
