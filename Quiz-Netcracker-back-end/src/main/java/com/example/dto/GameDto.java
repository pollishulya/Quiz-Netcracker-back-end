package com.example.dto;

import com.example.model.Question;
import com.example.model.User;
import lombok.Builder;
import lombok.Value;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Builder
@Value
public class GameDto {
    private UUID id;
    private String name;
    private String description;
    private User user;
    private Set<Question> questions;
}
