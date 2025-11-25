package com.ethiofintech.expense_tracker.repository;

import com.ethiofintech.expense_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // Finds a user by their email (needed for Login)
    Optional<User> findByEmail(String email);
}