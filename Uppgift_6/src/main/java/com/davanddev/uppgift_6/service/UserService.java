package com.davanddev.uppgift_6.service;

import com.davanddev.uppgift_6.model.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserService {
    private final Map<String, User> userStorage = new ConcurrentHashMap<>();

    public boolean registerUser(User user) {
        if (userStorage.containsKey(user.getEmail())) {
            return false; //User already exists
        }
        userStorage.put(user.getEmail(), user);
        return true;
    }

    public User getUserByEmail(String email) {
        return userStorage.get(email);
    }

    public boolean validateUser(String email, String password) {
        User user = userStorage.get(email);
        return user != null && user.getPassword().equals(password);
    }
}
