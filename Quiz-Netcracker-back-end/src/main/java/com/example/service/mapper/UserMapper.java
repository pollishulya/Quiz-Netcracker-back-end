package com.example.service.mapper;

import com.example.dto.UserDto;
import com.example.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserMapper {
    public UserDto toUserDto(final User user){
        return UserDto.builder()
                .id(user.getId())
                .mail(user.getMail())
                .login(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole())
                .games(user.getGame())
                .build();
    }

    public User fromUserDto(final UserDto userDto){
        return User.builder()
                .id(userDto.getId())
                .mail(userDto.getMail())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .role(userDto.getPassword())
                .game(userDto.getGames())
                .build();
    }

    public UserDto toShortDto(final User user){
        return UserDto.builder()
                .login(user.getLogin())
                .roles(user.getRole())
                .build();
    }
}
