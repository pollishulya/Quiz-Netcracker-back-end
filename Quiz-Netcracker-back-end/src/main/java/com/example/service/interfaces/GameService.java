package com.example.service.interfaces;

import com.example.model.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game createGame(Game game);
    void deleteGame(UUID id);
    Game updateGame(UUID id, Game game);
//    Game findGameByTitle(String name);
    Game findGameById(UUID id);
    List<Game> findAllGames();
}
