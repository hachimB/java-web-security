package com.example.backend.service;

import com.example.backend.dto.UserRegistrationRequest;
import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register a user with an optional admin role
    public User registerUser(UserRegistrationRequest registrationRequest) {
        User user = new User();
        user.setEmail(registrationRequest.getUserEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getUserPassword()));
        user.setUsername(registrationRequest.getUserName());
        user.setSurname(registrationRequest.getUserSurname());

        // Handle role: if admin is passed, assign 'admin', otherwise 'user'
        if (registrationRequest.getRole() != null && registrationRequest.getRole().equals("admin")) {
            user.setRole("admin");
        } else {
            user.setRole("user");
        }

        return userRepository.save(user);
    }

    public User loginUser(String userEmail, String userPassword) {
        return userRepository.findByUserEmail(userEmail).filter(user ->
                passwordEncoder.matches(userPassword, user.getPassword())
        ).orElseThrow(() -> new RuntimeException("Invalid credentials"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setSurname(updatedUser.getSurname());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRole(updatedUser.getRole());
        return userRepository.save(existingUser);
    }
}
