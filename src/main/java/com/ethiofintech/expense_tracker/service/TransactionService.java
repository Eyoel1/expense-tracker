package com.ethiofintech.expense_tracker.service;

import com.ethiofintech.expense_tracker.model.Transaction;
import com.ethiofintech.expense_tracker.model.User;
import com.ethiofintech.expense_tracker.repository.TransactionRepository;
import com.ethiofintech.expense_tracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    // 1. Save a new Expense/Income
    public Transaction saveTransaction(Transaction transaction, Long userId) {
        // Fetch the user from the DB to link them
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        transaction.setUser(user); // Connect the relationship
        return transactionRepository.save(transaction);
    }

    // 2. Get all my transactions
    public List<Transaction> getTransactionsForUser(Long userId) {
        return transactionRepository.findByUserIdOrderByDateDesc(userId);
    }

    // 3. Delete a transaction
    public void deleteTransaction(Long id) {
        transactionRepository.deleteById(id);
    }
}