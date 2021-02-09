package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CategoryDto {
    UUID id;
    @NotBlank(message = "message.")
    String title;
    @NotBlank
    String description;
}
