package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Game;
import com.example.repository.GameRepository;
import com.example.service.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game updateGame(UUID id, Game gameReq)
    {
        return gameRepository.findById(id).map(game->{
            game.setDescription(gameReq.getDescription());
            return  gameRepository.save(game);
        }).orElseThrow(()-> new ResourceNotFoundException("Object not found"));
    }

    @Override
    public Game findGameById(UUID id) {
        return gameRepository.findGameById(id);
    }

    @Override
    public Game findGameByName(String name) {
        return gameRepository.findGameByName(name);
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }
}