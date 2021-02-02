package com.example.service.interfaces;

import com.example.model.Game;
import com.example.model.GameAccess;
import com.example.model.Player;
import com.example.model.User;

import java.util.List;
import java.util.UUID;

public interface GameAccessService {
  //  Game createGameAccess(Game game);
  Game createGameAccessByGame(UUID id);
  Player createGameAccessByPlayer(UUID id);
  boolean activateGame(String code);
 GameAccess checkAccess(UUID gameId, UUID playerId);
//    void deleteGame(UUID id);
//    Game updateGame(UUID id, Game game);
//    Game findGameByTitle(String name);
//    Game findGameById(UUID id);
//    List<Game> findAllGames();
//    List<Game> searchGamesByTitle(String title);
}
