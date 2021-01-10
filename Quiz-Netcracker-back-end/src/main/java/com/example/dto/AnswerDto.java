package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnswerDto {
    UUID id;
    String title;
    Boolean right;
    UUID question;
}


