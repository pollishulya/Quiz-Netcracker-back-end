package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDto {
    UUID id;
    String mail;
    String password;
    String login;
    String role;
    UUID player;
    boolean active;
}
