package com.example.service.impl;

import com.example.model.GameRoom;
import com.example.model.Player;
import com.example.repository.GameRoomRepository;
import com.example.service.interfaces.GameRoomService;
import com.example.service.interfaces.GameService;
import com.example.service.interfaces.PlayerService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class GameRoomServiceImpl implements GameRoomService {
    private final GameRoomRepository gameRoomRepository;
    private final PlayerService playerService;
    private final GameService gameService;

    public GameRoomServiceImpl(GameRoomRepository gameRoomRepository, PlayerService playerService, GameService gameService) {
        this.gameRoomRepository = gameRoomRepository;
        this.playerService = playerService;
        this.gameService = gameService;
    }

    @Override
    public GameRoom findById(UUID id) {
        return gameRoomRepository.findGameRoomById(id);
    }

    @Override
    public GameRoom save(GameRoom gameRoom) {
        return gameRoomRepository.save(gameRoom);
    }

    @Override
    public GameRoom findGameRoom(UUID gameId, UUID playerId) {
        Player player = playerService.findPlayerById(playerId);
        List<GameRoom> gameRooms = gameRoomRepository.findGameRoomByGameId(gameId);
        for (GameRoom gameRoom : gameRooms) {
            if (gameRoom.getPlayers().size() < 2) {
                gameRoom.getPlayers().add(player);
                return save(gameRoom);
            }
        }
        return save(new GameRoom(gameService.findGameById(gameId), Collections.singleton(player)));
    }

    @Override
    public void delete(UUID id) {
        gameRoomRepository.deleteById(id);
    }


}
