// src/main/java/com/example/demo/service/UserService.java
package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByEmailAndPassword(String email, String password) {
        String queryString = "SELECT * FROM user WHERE email = '" + email + "' AND password = '" + password + "'";
        Query query = entityManager.createNativeQuery(queryString, User.class);
        @SuppressWarnings("unchecked")
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public User findByEmailAndPasswordSecure(String email, String password) {
        String queryString = "SELECT * FROM user WHERE email = :email AND password = :password";
        Query query = entityManager.createNativeQuery(queryString, User.class);
        query.setParameter("email", email);
        query.setParameter("password", password);
        @SuppressWarnings("unchecked")
        List<User> users = query.getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
