package com.example.service.interfaces;

import com.example.dto.PlayerDto;
import com.example.model.Game;
import com.example.model.GameAccess;
import com.example.model.Player;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

public interface GameAccessService {
    Game createGameAccessByGame(UUID id);

    Player createGameAccessByPlayer(UUID id);

    //  GameAccess activate(UUID gameId, UUID playerId);
    GameAccess checkAccess(UUID gameId, UUID playerId);

    List<Player> getPlayersWithTrueAccess(UUID gameId);

    List<Player> getPlayersWithFalseAccess(UUID gameId);

    GameAccess activateGame(String code);

    String sendActivateCode(UUID gameId, UUID playerId);

    GameAccess deactivateGame(UUID gameId, UUID playerId);

    GameAccess getGameAccess(UUID gameId, UUID playerId);

    List<GameAccess> deleteGameAccess(UUID gameId);

    List<GameAccess> getGameAccessesByGameId(UUID gameId);

    public List<GameAccess> updateGameAccess(Game game);

    void delete(UUID id);
}
