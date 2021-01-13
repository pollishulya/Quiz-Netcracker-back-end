package com.example.dto;

import com.example.service.validation.annotation.AnswersSetConstraint;
import com.example.service.validation.group.CreateValidationGroup;
import com.example.service.validation.group.UpdateValidationGroup;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AnswersSetConstraint(groups = {CreateValidationGroup.class, UpdateValidationGroup.class})
public class QuestionDto {
    UUID id;
    @NotNull(message = "Shouldn't be null", groups = {CreateValidationGroup.class, UpdateValidationGroup.class} )
    String title;
    @NotNull(message = "Shouldn't be null", groups = {CreateValidationGroup.class, UpdateValidationGroup.class} )
    String description;
    UUID category;
    UUID level;
    UUID game;
    Set<@Valid AnswerDto> answersSet;
}
