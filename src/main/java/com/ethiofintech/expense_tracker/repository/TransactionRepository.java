package com.ethiofintech.expense_tracker.repository;

import com.ethiofintech.expense_tracker.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Select * from transactions where user_id = ? order by date desc
    // "Desc" means show the newest expenses (today) first.
    List<Transaction> findByUserIdOrderByDateDesc(Long userId);
}