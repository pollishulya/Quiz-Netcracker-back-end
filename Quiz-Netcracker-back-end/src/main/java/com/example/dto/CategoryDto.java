package com.example.dto;

import com.example.model.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {
    UUID id;
    String name;
    String description;
    Set<Question> question;
}
