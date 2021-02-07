package com.example.controller;


import com.example.dto.GameAccessDto;
import com.example.dto.GameDto;
import com.example.dto.PlayerDto;
import com.example.exception.InvalidGameAccessException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.GameAccess;
import com.example.service.interfaces.GameAccessService;
import com.example.service.mapper.GameAccessMapper;
import com.example.service.mapper.GameMapper;
import com.example.service.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/game-access")
public class GameAccessController {
    private final PlayerMapper playerMapper;
    private final GameMapper gameMapper;
    private final GameAccessMapper gameAccessMapper;
    private final GameAccessService gameAccessService;
    private final MessageSource messageSource;

    @Autowired
    public GameAccessController( PlayerMapper playerMapper, GameMapper gameMapper, GameAccessMapper gameAccessMapper,
                                 GameAccessService gameAccessService, MessageSource messageSource) {
        this.gameMapper = gameMapper;
        this.playerMapper = playerMapper;
        this.gameAccessMapper = gameAccessMapper;
        this.gameAccessService = gameAccessService;
        this.messageSource = messageSource;
    }

    @GetMapping("/players/{userId}")
    public PlayerDto findPlayers(@PathVariable UUID userId) {
        return playerMapper.toDto(gameAccessService.createGameAccessByPlayer(userId));
    }

    @GetMapping("/games/{userId}")
    public GameDto findGames(@PathVariable UUID userId) {
        return gameMapper.toDto(gameAccessService.createGameAccessByGame(userId));
    }

//    @GetMapping("/activate/{gameId}/{playerId}")
//    public boolean activate(@PathVariable UUID playerId, @PathVariable UUID gameId) {
//        GameAccess gameAccess=gameAccessService.activate(gameId, playerId);
//        return gameAccess.isAccess();
//    }
    @GetMapping("/{gameId}/{playerId}")
    public GameAccessDto getGameAccess(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        return gameAccessMapper.toDto(gameAccessService.getGameAccess(gameId, playerId));
    }

    @GetMapping("/sendActivateCode/{gameId}/{playerId}")
    public String sendActivateCode(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        return gameAccessService.sendActivateCode(gameId, playerId);
    }

        @GetMapping("/activate/{code}")
    public boolean activate(@PathVariable String code) {
        GameAccess gameAccess=gameAccessService.activateGame(code);
        return gameAccess.isAccess();
    }

    @GetMapping("/deactivate/{gameId}/{playerId}")
    public GameAccessDto deactivate(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        return gameAccessMapper.toDto(gameAccessService.deactivateGame(gameId, playerId));
    }

    @GetMapping("/check/{gameId}/{playerId}")
    public boolean checkAccess(@PathVariable UUID playerId, @PathVariable UUID gameId) {
        GameAccess gameAccess=gameAccessService.checkAccess(gameId, playerId);
        if (gameAccess == null) {
            throw new InvalidGameAccessException(ErrorInfo.INVALID_GAME_ACCESS_ERROR,
                    messageSource.getMessage("message.InvalidGameAccessError", null, LocaleContextHolder.getLocale()));
        }
        return gameAccess.isAccess();
    }

    @GetMapping("/findPlayersWithTrueAccess/{gameId}")
    public List<PlayerDto> getPlayersByGameWithTrueAccess( @PathVariable UUID gameId) {
        return gameAccessService.getPlayersWithTrueAccess(gameId).stream().map(playerMapper::toDto).collect(Collectors.toList());
    }

    @GetMapping("/findPlayersWithFalseAccess/{gameId}")
    public List<PlayerDto> getPlayersByGameWithFalseAccess( @PathVariable UUID gameId) {
        return gameAccessService.getPlayersWithFalseAccess(gameId).stream().map(playerMapper::toDto).collect(Collectors.toList());
    }

}