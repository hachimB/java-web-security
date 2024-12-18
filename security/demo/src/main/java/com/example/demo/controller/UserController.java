// src/main/java/com/example/demo/controller/UserController.java
package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/signin")
    public User loginUser(@RequestBody User user) {
        // Vulnerable to SQL Injection
        return userService.findByEmailAndPassword(user.getEmail(), user.getPassword());
    }

    @PostMapping("/signinsecure")
    public User loginUserSecure(@RequestBody User user) {
        return userService.findByEmailAndPasswordSecure(user.getEmail(), user.getPassword());
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
