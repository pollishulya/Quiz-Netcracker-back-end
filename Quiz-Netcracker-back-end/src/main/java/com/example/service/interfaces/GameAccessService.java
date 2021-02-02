package com.example.service.interfaces;

import com.example.model.Game;
import com.example.model.GameAccess;
import com.example.model.Player;
import java.util.UUID;

public interface GameAccessService {
  Game createGameAccessByGame(UUID id);
  Player createGameAccessByPlayer(UUID id);
  GameAccess activate(UUID gameId, UUID playerId);
  GameAccess checkAccess(UUID gameId, UUID playerId);
}
