package com.example.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RoleDto {
    private UUID id;
    private String title;

//    private Set<UserDto> usersSet;
}
