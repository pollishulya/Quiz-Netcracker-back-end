package com.example.controller;

import com.example.dto.GameRoomDto;
import com.example.model.GameRoom;
import com.example.model.LikePlayer;
import com.example.model.Player;
import com.example.service.interfaces.GameRoomService;
import com.example.service.mapper.GameRoomMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class GameplayController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameRoomService gameRoomService;
    private final GameRoomMapper mapper;

    public GameplayController(SimpMessagingTemplate simpMessagingTemplate, GameRoomService gameRoomService, GameRoomMapper mapper) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.gameRoomService = gameRoomService;
        this.mapper = mapper;
    }

    @MessageMapping("/gameplay")
    public void send(@Payload LikePlayer likePlayer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player player : gameRoomService.findById(likePlayer.getGameRoomId()).getPlayers()) {
            if (likePlayer.getRecipientId().equals(player.getId())) {
                simpMessagingTemplate.convertAndSend("/topic/game/" + likePlayer.getRecipientId(), objectMapper.writeValueAsString(likePlayer.getName()));
            }
        }
    }

    @MessageMapping("/game-room/close")
    public void sendPlayerExited(@Payload GameRoomDto gameRoomDto) throws Exception {
        GameRoom gameRoom = mapper.toEntity(gameRoomDto);
        ObjectMapper mapper = new ObjectMapper();
        for (Player player : gameRoom.getPlayers()) {
            simpMessagingTemplate.convertAndSend("/topic/game/" + player.getId(), mapper.writeValueAsString(gameRoom.getPlayers()));
        }
    }
}
