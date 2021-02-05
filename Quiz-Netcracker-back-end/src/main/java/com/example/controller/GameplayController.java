package com.example.controller;

import com.example.dto.GameRoomDto;
import com.example.model.GameRoom;
import com.example.dto.ActionsToPlayer;
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

    @MessageMapping("/like-player")
    public void send(@Payload ActionsToPlayer actionsToPlayer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player player : gameRoomService.findById(actionsToPlayer.getGameRoomId()).getPlayers()) {
            if (actionsToPlayer.getRecipientId().equals(player.getId())) {
                simpMessagingTemplate.convertAndSend("/topic/game/" + actionsToPlayer.getRecipientId(), objectMapper.writeValueAsString(actionsToPlayer.getName()));
            }
        }
    }

    @MessageMapping("/go-game")
    public void sendGoGame(@Payload GameRoomDto gameRoomDto) throws JsonProcessingException {
        GameRoom gameRoom = mapper.toEntity(gameRoomDto);
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player player : gameRoom.getPlayers()) {
            simpMessagingTemplate.convertAndSend("/topic/game/" + player.getId(), objectMapper.writeValueAsString("go"));
        }
    }

    @MessageMapping("/next-question")
    public void sendNextQuestion(@Payload GameRoomDto gameRoomDto) throws JsonProcessingException {
        GameRoom gameRoom = mapper.toEntity(gameRoomDto);
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player player : gameRoom.getPlayers()) {
            simpMessagingTemplate.convertAndSend("/topic/game/" + player.getId(), objectMapper.writeValueAsString("next"));
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

    @MessageMapping("/delete-player")
    public void sendDeletePlayer(@Payload ActionsToPlayer actionsToPlayer) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player player : gameRoomService.findById(actionsToPlayer.getGameRoomId()).getPlayers()) {
            if (actionsToPlayer.getRecipientId().equals(player.getId())) {
                simpMessagingTemplate.convertAndSend("/topic/game/" + actionsToPlayer.getRecipientId(), objectMapper.writeValueAsString("delete"));
            }
        }
    }
}
