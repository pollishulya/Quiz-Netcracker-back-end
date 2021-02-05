package com.example.service.impl;

import com.example.dto.PlayerDto;
import com.example.model.Game;
import com.example.model.GameAccess;
import com.example.model.Player;
import com.example.repository.GameAccessRepository;
import com.example.repository.GameRepository;
import com.example.repository.PlayerRepository;
import com.example.service.interfaces.GameAccessService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameAccessServiceImpl implements GameAccessService {

    private final GameAccessRepository gameAccessRepository;
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;

    public GameAccessServiceImpl(GameAccessRepository gameAccessRepository, PlayerRepository playerRepository, GameRepository gameRepository) {
        this.gameAccessRepository = gameAccessRepository;
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public Game createGameAccessByGame(UUID id) {
        Game game=gameRepository.findGameById(id);
        if(game.getAccess().equals("PRIVATE")) {
            List<Player> players = playerRepository.findAll()
                    .stream()
                    .peek(player -> {
                        GameAccess gameAccess = new GameAccess();
                        gameAccess.setGame(game);
                        gameAccess.setPlayer(player);
                        if(game.getPlayer().getId()==player.getId()) {
                            gameAccess.setAccess(true);
                        }
                        else {
                            gameAccess.setAccess(false);
                        }
                        gameAccess.setActivationCode(UUID.randomUUID().toString());
                        gameAccessRepository.save(gameAccess);
                    })
                    .collect(Collectors.toList());
        }
        return game;
    }

    @Override
    public Player createGameAccessByPlayer(UUID id) {
        Player player=playerRepository.getPlayerByUserId(id);
        List<Game> games = gameRepository.findAll()
                .stream()
                .peek(game -> {
                    if(game.getAccess()=="PRIVATE") {
                        GameAccess gameAccess = new GameAccess();
                        gameAccess.setGame(game);
                        gameAccess.setPlayer(player);
                        gameAccess.setAccess(false);
                        gameAccess.setActivationCode(UUID.randomUUID().toString());
                        gameAccessRepository.save(gameAccess);
                    }
                })
                .collect(Collectors.toList());
        return player;
    }

    @Override
    public GameAccess activate(UUID gameId, UUID playerId) {
        GameAccess gameAccess = gameAccessRepository.findGameAccessesByGameIdAndPlayerId(gameId,playerId);
        if (gameAccess == null) {
            return null;
        }
        if(gameAccess.isAccess()) {
            gameAccess.setAccess(false);
        }
        else{
            gameAccess.setAccess(true);
        }
        gameAccessRepository.save(gameAccess);
        return gameAccess;
    }

    @Override
    public GameAccess checkAccess(UUID gameId, UUID playerId){
       GameAccess gameAccess = gameAccessRepository.findGameAccessesByGameIdAndPlayerId(gameId,playerId);
        return gameAccess;
    }

    @Override
    public List<Player> getPlayersWithTrueAccess(UUID gameId){
        List<Player> players = new LinkedList<Player>();
        List<GameAccess> gameAccesses = gameAccessRepository.findGameAccessesByGameId(gameId)
                .stream()
                .peek(gameAccess -> {
                    if(gameAccess.isAccess()) {
                        Player player = playerRepository.findPlayerById(gameAccess.getPlayer().getId());
                        players.add(player);
                    }
                })
                .collect(Collectors.toList());
        return players;
    }
    @Override
    public List<Player> getPlayersWithFalseAccess(UUID gameId){
        List<Player> players = new LinkedList<Player>();
        List<GameAccess> gameAccesses = gameAccessRepository.findGameAccessesByGameId(gameId)
                .stream()
                .peek(gameAccess -> {
                    if(!gameAccess.isAccess()) {
                        Player player = playerRepository.findPlayerById(gameAccess.getPlayer().getId());
                        players.add(player);
                    }
                })
                .collect(Collectors.toList());
        return players;
    }
}
