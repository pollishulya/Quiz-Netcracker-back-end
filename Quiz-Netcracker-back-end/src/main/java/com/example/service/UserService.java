package com.example.service;

import com.example.model.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<User> findAllUser();
    User saveUser(User user);
    User updateUser(UUID userId, User userRequest);
    void deleteUser(UUID userId);

    Optional<User> getUserById(UUID userId);
}
