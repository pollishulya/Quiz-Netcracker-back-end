package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
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
