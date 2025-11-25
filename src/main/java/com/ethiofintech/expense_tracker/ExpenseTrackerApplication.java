package com.ethiofintech.expense_tracker;

import com.ethiofintech.expense_tracker.model.User;
import com.ethiofintech.expense_tracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ExpenseTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseTrackerApplication.class, args);
	}

	// This runs every time the server starts
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository) {
		return args -> {
			// Check if we have users, if not, create a Test User
			if (userRepository.count() == 0) {
				User testUser = new User();
				testUser.setFullName("Abebe Beso");
				testUser.setEmail("abebe@gmail.com");
				testUser.setPasswordHash("hashedpassword123");
				userRepository.save(testUser);
				System.out.println("✅ TEST USER CREATED! ID = 1");
			}
		};
	}
}