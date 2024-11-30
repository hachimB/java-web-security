package com.example.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String userPassword;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String userSurname;

    @Column(nullable = false)
    private String role;

    // Getters and Setters
    public Long getId() { return userId; }
    public void setId(Long id) { this.userId = id; }
    public String getEmail() { return userEmail; }
    public void setEmail(String email) { this.userEmail = email; }
    public String getPassword() { return userPassword; }
    public void setPassword(String password) { this.userPassword = password; }
    public String getUsername() { return userName; }
    public void setUsername(String username) { this.userName = username; }
    public String getSurname() { return userSurname; }
    public void setSurname(String surname) { this.userSurname = surname; }
    
    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}
}
