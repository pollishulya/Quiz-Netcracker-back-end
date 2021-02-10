package com.example.dto;

import com.example.service.validation.group.Update;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class PlayerDto {
    UUID id;
    String name;
    String email;
    String photo;
    UUID user;
}
