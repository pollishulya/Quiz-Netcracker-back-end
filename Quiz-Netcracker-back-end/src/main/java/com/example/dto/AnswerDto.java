package com.example.dto;

import com.example.model.Question;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnswerDto {

    UUID id;
    String title;
    Boolean right;
    UUID question;

}
