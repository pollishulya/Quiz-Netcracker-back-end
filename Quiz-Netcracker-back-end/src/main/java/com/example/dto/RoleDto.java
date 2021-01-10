package com.example.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RoleDto {
    UUID id;
    String title;
    Set<UUID> users;
}
