package com.app_movie.app.movie.repository;

import com.app_movie.app.movie.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {
}
