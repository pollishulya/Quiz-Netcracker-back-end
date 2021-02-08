package com.example.service.interfaces;

import com.example.model.GameRoom;

import java.util.List;
import java.util.UUID;

public interface GameRoomService {
    GameRoom findById(UUID id);

    GameRoom save(GameRoom gameRoom);

    GameRoom findGameRoom(UUID gameId, UUID playerId);

    void delete(UUID id);

    List<GameRoom> findByGameId(UUID id);
}
