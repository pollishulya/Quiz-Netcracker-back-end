package com.example.dto;

import com.example.model.Question;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;
@Builder
@Value
public class LevelDto {
    private UUID id;
    private String title;
    private String description;
    private Set<Question> questions;
}
