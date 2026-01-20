package com.example.birrwise.repository;

import com.example.birrwise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // We only need to find a user by email to allow them to login
    Optional<User> findByEmail(String email);

    // Check if email exists to prevent duplicates during registration
    Boolean existsByEmail(String email);
}