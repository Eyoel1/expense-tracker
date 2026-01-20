package com.example.birrwise.controller;

import com.example.birrwise.model.User;
import com.example.birrwise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    // 1. REGISTER
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Error: Email is already in use!");
            return ResponseEntity.badRequest().body(response);
        }
        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        return ResponseEntity.ok(response);
    }

    // 2. LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginDetails) {
        User user = userRepository.findByEmail(loginDetails.getEmail()).orElse(null);

        if (user != null && user.getPassword().equals(loginDetails.getPassword())) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Login Successful");
            response.put("fullName", user.getFullName());
            response.put("userId", String.valueOf(user.getId()));
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body(null);
    }

    // 3. UPDATE PROFILE (Set Salary)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Save new salary
        if (userDetails.getMonthlySalary() != null) {
            user.setMonthlySalary(userDetails.getMonthlySalary());
        }

        userRepository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Profile Updated Successfully");
        return ResponseEntity.ok(response);
    }
}