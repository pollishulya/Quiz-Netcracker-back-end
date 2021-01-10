package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class QuestionDto {
    UUID id;
    String title;
    String description;
    UUID category;
    UUID level;
    UUID game;
    Set<UUID> answersSet;
}
