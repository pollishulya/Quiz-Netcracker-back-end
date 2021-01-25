package com.example.controller;

import com.example.dto.GameRoomDto;
import com.example.model.GameMessage;
import com.example.model.GameRoom;
import com.example.model.Player;
import com.example.service.interfaces.GameRoomService;
import com.example.service.mapper.GameRoomMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class GameplayController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameRoomMapper gameRoomMapper;
    private final GameRoomService gameRoomService;

    public GameplayController(SimpMessagingTemplate simpMessagingTemplate, GameRoomMapper gameRoomMapper, GameRoomService gameRoomService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.gameRoomMapper = gameRoomMapper;
        this.gameRoomService = gameRoomService;
    }

    @MessageMapping("/gameplay")
    public void send(@Payload GameMessage gameMessage) throws Exception {
        Thread.sleep(2000);
        GameRoom gameRoom = gameRoomService.findById(gameMessage.getGameRoomId());
        for(Player player: gameRoom.getPlayers()){
            if(!gameMessage.getSenderId().equals(player.getId())){
                simpMessagingTemplate.convertAndSend("/topic/game/" + player.getId(), gameMessage);
            }
        }

    }

    @PostMapping("/game-room/save")
    public GameRoomDto save(@RequestBody GameRoomDto gameRoomDto){
        GameRoom gameRoom = gameRoomMapper.toEntity(gameRoomDto);
        return gameRoomMapper.toDto(gameRoomService.save(gameRoom));
    }

    @GetMapping("/game-room/{gameId}/{playerId}")
    public GameRoomDto findGameRoom(@PathVariable UUID gameId, @PathVariable UUID playerId){
        return gameRoomMapper.toDto(gameRoomService.findGameRoom(gameId, playerId));
    }

    @GetMapping("/game-room/{id}")
    public GameRoomDto findGameRoomById(@PathVariable UUID id){
        return gameRoomMapper.toDto(gameRoomService.findById(id));
    }

    @DeleteMapping("/game-room/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        gameRoomService.delete(id);
        return ResponseEntity.ok().build();
    }
}
