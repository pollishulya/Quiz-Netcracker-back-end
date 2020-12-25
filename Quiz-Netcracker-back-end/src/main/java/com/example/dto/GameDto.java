package com.example.dto;

import com.example.model.Question;
import com.example.model.User;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class GameDto {
    UUID id;
    String name;
    String description;
    User user;
}
