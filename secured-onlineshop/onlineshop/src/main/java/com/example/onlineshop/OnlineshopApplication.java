package com.example.onlineshop;

import com.example.onlineshop.entities.User;
import com.example.onlineshop.enums.UserRole;
import com.example.onlineshop.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class OnlineshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineshopApplication.class, args);
	}
	/*
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.count() == 0) {
				User admin = new User();
				admin.setFirstName("Admin");
				admin.setLastName("User");
				admin.setAge(30);
				admin.setEmail("admin@example.com");
				admin.setPassword(passwordEncoder.encode("admin123")); // Set a strong password here
				admin.setRole(UserRole.ADMIN);
				userRepository.save(admin);
			}
		};
	}

	 */

}
