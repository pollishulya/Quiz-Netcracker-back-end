package com.example.service.interfaces;

import com.example.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAllUser();
    User saveUser(User user);
    User updateUser(UUID userId, User userRequest);
    void deleteUser(UUID userId);

    User getUserById(UUID userId);
}