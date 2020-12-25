package com.example.dto;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Builder
@Value
public class AnswerDto {

    UUID id;
    String title;
    boolean right;

}
