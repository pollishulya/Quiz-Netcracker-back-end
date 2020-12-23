package com.example.dto;

import com.example.model.Question;
import com.example.model.User;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class GameDto {

    private UUID id;
    private String Description;
    private String name;
    private Set<Question> questionsSet;

    //private User user;
    // waiting for UserDto
}
