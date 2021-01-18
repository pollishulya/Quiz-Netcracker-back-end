package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Game;
import com.example.repository.GameRepository;
import com.example.service.interfaces.GameService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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


    public List<Game> searchGamesByTitle(String title) {
        return gameRepository.findAllByTitleContaining(title);
    }

    @Override
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game updateGame(UUID id, Game gameReq)
    {
        return gameRepository.findById(id).map(game->{
            game.setTitle(gameReq.getTitle());
            game.setDescription(gameReq.getDescription());
            return  gameRepository.save(game);
        }).orElseThrow(()-> new ResourceNotFoundException("Object not found"));
    }

    @Override
    public Game findGameById(UUID id) {
        return gameRepository.findGameById(id);
    }

    @Override
    public Game findGameByTitle(String title) {
        return gameRepository.findGameByTitle(title);
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }


}