package com.example.dto;

import com.example.model.Game;
import com.example.model.GameCategory;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GameDto {
    UUID id;
    String title;
    String description;
    Set<QuestionDto> questions;
    UUID player;
    String photo;

//    UUID gameCategory;
    Long averageRating;
    Long views;
    Long ratingCount;
    String access;
}
