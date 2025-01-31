package com.app_movie.app.movie.repository;

import com.app_movie.app.movie.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);

    @Query("UPDATE User u SET u.password = ?2 WHERE u.email = ?1")
    public void updateUserPassword(String email, String password);
}
