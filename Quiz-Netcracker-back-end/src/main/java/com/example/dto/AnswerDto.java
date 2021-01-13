package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import com.example.service.validation.group.CreateValidationGroup;
import com.example.service.validation.group.UpdateValidationGroup;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnswerDto {
    UUID id;
    @NotEmpty(message = "Shouldn't be null or empty", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    String title;
    @NotNull(message = "Shouldn't be null", groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
    Boolean right;
    UUID question;
}


