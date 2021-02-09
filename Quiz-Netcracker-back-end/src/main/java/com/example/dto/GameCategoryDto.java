package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GameCategoryDto {
    UUID id;
    @NotBlank(message = "message.InvalidGameCategoryTitle")
    String title;
    @NotBlank(message = "message.InvalidGameCategoryDescription")
    String description;
}

