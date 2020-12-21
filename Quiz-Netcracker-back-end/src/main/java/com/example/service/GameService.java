package com.example.service;

import com.example.model.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game createGame(Game game);
    void deleteGame(UUID id);
    Game updateGame(UUID id, Game game);
    Game findGameByName(String name);
    Game findGameById(UUID id);
    List<Game> findAllGames();
}
