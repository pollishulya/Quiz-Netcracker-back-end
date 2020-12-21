package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/findAllUsers")
    public CollectionWrapper<User> getUsers() {
        return new CollectionWrapper<>(userService.findAllUser());
    }

    @GetMapping("/findUser/{userId}")
    public Optional<User> getUsers(@PathVariable UUID userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/save")
    public User createUser(@Valid @RequestBody User user) {
        return userService.saveUser(user);
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable UUID userId,
                                   @Valid @RequestBody User userRequest) {
        return userService.updateUser(userId, userRequest);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}