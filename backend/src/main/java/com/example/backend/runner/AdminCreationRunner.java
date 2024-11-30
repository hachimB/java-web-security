package com.example.backend.runner;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminCreationRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        // Check if there's no admin user in the database
        if (userRepository.findByUserEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin")); // Hash password
            admin.setUsername("Admin");
            admin.setSurname("Ad");
            admin.setRole("admin"); // Set role as admin
            userRepository.save(admin);
            System.out.println("Admin account created successfully.");
        }
    }
}
