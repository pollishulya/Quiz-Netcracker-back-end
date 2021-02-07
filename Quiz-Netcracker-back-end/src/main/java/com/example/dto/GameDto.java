package com.example.dto;

import com.example.model.GameCategory;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GameDto {
    UUID id;
    @NotBlank(message = "message.GameTitleNotValid", groups = {Create.class, Update.class})
    String title;
    @NotBlank(message = "message.GameDescriptionNotValid", groups = {Create.class, Update.class})
    String description;
    Set<@Valid QuestionDto> questions;
    @NotNull(message = "message.PlayerIdNotValid", groups = {Create.class, Update.class})
    UUID player;
    String photo;
    UUID gameCategory;
    Long averageRating;
    Long views;
    Long ratingCount;
    @NotNull(message = "message.AccessNotValid", groups = {Create.class, Update.class})
    String access;
}
