package com.example.birrwise.controller;

import com.example.birrwise.model.Expense;
import com.example.birrwise.model.User;
import com.example.birrwise.repository.ExpenseRepository;
import com.example.birrwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    ExpenseRepository expenseRepository;

    @Autowired
    UserRepository userRepository;

    // 1. ADD EXPENSE
    @PostMapping("/add")
    public ResponseEntity<?> addExpense(@RequestBody Expense expense) {
        if (expense.getDate() == null) {
            expense.setDate(LocalDate.now());
        }
        expenseRepository.save(expense);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Expense Added Successfully");
        return ResponseEntity.ok(response);
    }

    // 2. GET USER EXPENSES (For Charts)
    @GetMapping("/user/{userId}")
    public List<Expense> getUserExpenses(@PathVariable Long userId) {
        return expenseRepository.findByUserId(userId);
    }

    // 3. GET CALCULATED SUMMARY
    @GetMapping("/summary/{userId}")
    public ResponseEntity<Map<String, Object>> getSummary(@PathVariable Long userId) {
        // Fetch User to get Real Salary
        User user = userRepository.findById(userId).orElse(null);
        double monthlyIncome = (user != null && user.getMonthlySalary() != null) ? user.getMonthlySalary() : 0.0;

        // Fetch Expenses
        List<Expense> expenses = expenseRepository.findByUserId(userId);

        double totalExpense = 0.0;
        for (Expense e : expenses) {
            totalExpense += e.getAmount();
        }

        double netBalance = monthlyIncome - totalExpense;

        Map<String, Object> summary = new HashMap<>();
        summary.put("income", monthlyIncome);
        summary.put("totalExpense", totalExpense);
        summary.put("balance", netBalance);
        summary.put("transactionCount", expenses.size());

        return ResponseEntity.ok(summary);
    }
}