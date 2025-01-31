package com.app_movie.app.movie.repository;

import com.app_movie.app.movie.entity.ForgotPassword;
import com.app_movie.app.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    @Query("SELECT fp FROM ForgotPassword WHERE fp.otp = ?1 AND fp.user = ?2")
    public Optional<ForgotPassword> findByOtpAndUser(String otp, User user);

}
