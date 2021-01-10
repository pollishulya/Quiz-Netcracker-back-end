package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LevelDto {
    UUID id;
    String title;
    String description;
    Set<UUID> questions;
}
