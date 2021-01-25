package com.example.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GameMessage {
    private String name;
    private UUID senderId;
    private boolean right;
    private UUID gameRoomId;
    private UUID answer;
}
