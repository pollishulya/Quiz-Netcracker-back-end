package com.example.dto;

import com.example.model.Game;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class UserDto {
    UUID  id;
    String mail;
    String password;
    String login;
    String roles;
    Set<Game> games;
}
