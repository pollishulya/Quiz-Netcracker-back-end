package com.example.controller;

import com.example.dto.UserDto;
import com.example.model.User;
import com.example.service.interfaces.UserService;
import com.example.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final Mapper mapper;
    @Autowired
    public UserController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/findAllUsers")
    public List<UserDto> getUsers() {
        return userService.findAllUser().stream().map(mapper:: toShortDto).collect(Collectors.toList());
    }

    @GetMapping("/findUser/{userId}")
    public UserDto getUsers(@PathVariable UUID userId) {
        return mapper.toShortDto(userService.getUserById(userId));
    }

    @PostMapping("/save")
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        User user= mapper.fromUserDto(userDto);
        return mapper.toUserDto(userService.saveUser(user));
    }

    @PutMapping("/update/{userId}")
    public UserDto updateUser(@PathVariable UUID userId,
                                   @Valid @RequestBody UserDto userDto) {
        User user= mapper.fromUserDto(userDto);
        return mapper.toUserDto(userService.updateUser(userId, user));
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}