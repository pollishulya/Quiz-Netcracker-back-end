package com.example.dto;

import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GameDto {
    UUID id;
    @NotEmpty(message = "Shouldn't be empty", groups = {Create.class, Update.class})
    String title;
    @NotEmpty(message = "Shouldn't be empty", groups = {Create.class, Update.class})
    String description;
    Set<@Valid QuestionDto> questions;
    UUID player;
}
