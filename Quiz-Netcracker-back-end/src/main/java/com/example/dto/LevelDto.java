package com.example.dto;

import com.example.model.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;
@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LevelDto {
    UUID id;
    String title;
    String description;
    Set<Question> questions;
}
