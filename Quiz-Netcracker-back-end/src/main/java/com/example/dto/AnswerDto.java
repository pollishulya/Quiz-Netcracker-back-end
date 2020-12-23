package com.example.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Builder
@Value
public class AnswerDto {

    private UUID id;
    private String title;
    private boolean right;

}
