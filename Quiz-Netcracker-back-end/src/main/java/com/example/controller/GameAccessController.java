package com.example.controller;


import com.example.dto.GameAccessDto;
import com.example.dto.GameDto;
import com.example.dto.PlayerDto;
import com.example.model.GameAccess;
import com.example.service.interfaces.GameAccessService;
import com.example.service.mapper.GameAccessMapper;
import com.example.service.mapper.GameMapper;
import com.example.service.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/game-access")
public class GameAccessController {
    private final PlayerMapper playerMapper;
    private final GameMapper gameMapper;
    private final GameAccessMapper gameAccessMapper;
    private final GameAccessService gameAccessService;

    @Autowired
    public GameAccessController( PlayerMapper playerMapper, GameMapper gameMapper, GameAccessMapper gameAccessMapper, GameAccessService gameAccessService) {
        this.gameMapper = gameMapper;
        this.playerMapper = playerMapper;
        this.gameAccessMapper = gameAccessMapper;
        this.gameAccessService = gameAccessService;
    }

    @GetMapping("/players/{userId}")
    public PlayerDto findPlayers(@PathVariable UUID userId) {
        return playerMapper.toDto(gameAccessService.createGameAccessByPlayer(userId));
    }

    @GetMapping("/games/{userId}")
    public GameDto findGames(@PathVariable UUID userId) {
        return gameMapper.toDto(gameAccessService.createGameAccessByGame(userId));
    }

    @GetMapping("/activate/{gameId}/{playerId}")
    public boolean activate(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        GameAccess gameAccess=gameAccessService.checkAccess(gameId, playerId);
        return gameAccess.isAccess();
    }

    @GetMapping("/check/{gameId}/{playerId}")
    public boolean checkAccess(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        GameAccess gameAccess=gameAccessService.checkAccess(gameId, playerId);
        return gameAccess.isAccess();
    }

}