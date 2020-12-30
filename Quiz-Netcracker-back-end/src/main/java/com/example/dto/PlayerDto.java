package com.example.dto;


import com.example.model.Game;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDto {
    UUID id;
    Set<UUID> games;
}
