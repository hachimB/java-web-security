package com.example.backend.controller;

import com.example.backend.dto.UserRegistrationRequest;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource; 
import java.sql.Connection;  
import java.sql.Statement;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    // Registration endpoint for normal users
    @PostMapping("/signup")
    public User signup(@RequestBody UserRegistrationRequest registrationRequest) {
        return userService.registerUser(registrationRequest);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRegistrationRequest loginRequest) {
        try {
            User user = userService.loginUser(loginRequest.getUserEmail(), loginRequest.getUserPassword());
            return ResponseEntity.ok(user); // Return user if login is successful
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid credentials"); // Return error if login fails
        }
    }

     @PostMapping("/create-admin")
    public ResponseEntity<?> createAdmin(@RequestBody UserRegistrationRequest registrationRequest) {
        // This should be protected by some authorization (e.g., only accessible by existing admins)
        registrationRequest.setRole("admin");  // Force role to admin
        User admin = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(admin);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers(Authentication authentication) {
    if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access denied");
    }
    return ResponseEntity.ok(userService.getAllUsers());
}


    // Delete a user by ID (only for admins)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // Update a user (only for admins)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Add a new user (only for admins)
    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody UserRegistrationRequest registrationRequest) {
        User newUser = userService.registerUser(registrationRequest);
        return ResponseEntity.ok(newUser);
    }


    @Autowired
    private DataSource dataSource;

    @PostMapping("/insecure-signup")

    public String insecureSignup(@RequestParam String userEmail,
                             @RequestParam String userName,
                             @RequestParam String userPassword,
                             @RequestParam String userSurname,
                             @RequestParam String role) {
    try (Connection conn = dataSource.getConnection()) {
        // Insecure: User inputs are directly concatenated into the SQL query
        String sql = "INSERT INTO users (user_email, user_name, user_password, user_surname, role) " +
                     "VALUES ('" + userEmail + "', '" + userName + "', '" + userPassword + "', '" + userSurname + "', '" + role + "')";
        
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(sql);
        return "Insecure Signup Successful!";
    } catch (Exception e) {
        e.printStackTrace();
        return "Error occurred: " + e.getMessage();
    }
}


}
