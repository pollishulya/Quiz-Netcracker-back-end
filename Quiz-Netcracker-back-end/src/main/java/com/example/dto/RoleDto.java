package com.example.dto;

import com.example.model.User;
import lombok.Builder;
import lombok.Value;

import java.util.Set;
import java.util.UUID;

@Value
@Builder
public class RoleDto {
    UUID id;
    String title;
    Set<User> users;
}
