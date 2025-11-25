package com.ethiofintech.expense_tracker.controller;

import com.ethiofintech.expense_tracker.model.Transaction;
import com.ethiofintech.expense_tracker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions") // Base URL
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // ANDROID SENDS: A Transaction JSON and a userId
    // SERVER DO: Saves it
    @PostMapping("/{userId}")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction, @PathVariable Long userId) {
        Transaction newTransaction = transactionService.saveTransaction(transaction, userId);
        return ResponseEntity.ok(newTransaction);
    }

    // ANDROID ASKS: Give me all transactions for user 1
    // SERVER DO: Returns List
    @GetMapping("/{userId}")
    public ResponseEntity<List<Transaction>> getUserTransactions(@PathVariable Long userId) {
        List<Transaction> transactions = transactionService.getTransactionsForUser(userId);
        return ResponseEntity.ok(transactions);
    }

    // ANDROID SENDS: Delete transaction 5
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.ok().build();
    }
}