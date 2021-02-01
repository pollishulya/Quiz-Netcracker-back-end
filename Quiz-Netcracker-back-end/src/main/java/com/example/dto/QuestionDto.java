package com.example.dto;

import com.example.service.validation.annotation.AnswersSetConstraint;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AnswersSetConstraint(groups = {Create.class, Update.class})
public class QuestionDto {
    UUID id;
    @NotNull(message = "message.QuestionTitleNotValid", groups = {Create.class, Update.class} )
    String title;
    @NotNull(message = "message.QuestionDescriptionNotValid", groups = {Create.class, Update.class} )
    String description;
    UUID category;
    UUID level;
    UUID game;
    String photo;
    Set<@Valid AnswerDto> answersSet;
}
