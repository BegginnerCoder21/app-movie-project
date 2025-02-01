package com.app_movie.app.movie.repository;

import com.app_movie.app.movie.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    @Transactional
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.id = ?1")
    void destroyRefreshTokenById(Long id);
}
