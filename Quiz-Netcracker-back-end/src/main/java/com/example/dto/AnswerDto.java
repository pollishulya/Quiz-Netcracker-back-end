package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class AnswerDto {
    UUID id;
    @NotEmpty(message = "Shouldn't be null or empty", groups = {Create.class, Update.class})
    String title;
    @NotNull(message = "Shouldn't be null", groups = {Create.class, Update.class})
    Boolean right;
    UUID question;
}


