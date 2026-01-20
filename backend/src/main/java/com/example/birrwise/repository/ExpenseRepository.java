package com.example.birrwise.repository;


import com.example.birrwise.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    // Find all expenses belonging to a specific user ID
    List<Expense> findByUserId(Long userId);
}