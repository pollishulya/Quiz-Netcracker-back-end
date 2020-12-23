package com.example.controller;

import com.example.dto.GameDto;
import com.example.model.Game;
import com.example.service.interfaces.GameService;
import com.example.service.mapper.GameMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = {"http://localhost:4200"})
public class GameController {
    public final GameService gameService;
    public final GameMapper gameMapper;

    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable UUID id) {
        return gameMapper.toShortGameDto(gameService.findGameById(id));
    }

    @GetMapping("/{name}")
    public GameDto getGameByName(@PathVariable String name) {
        return gameMapper.toGameDto(gameService.findGameByName(name));
    }

    @GetMapping("/all")
    public List<GameDto> getGames() {
        return gameService.findAllGames().stream()
                .map(gameMapper :: toShortGameDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/game")
    public GameDto createGame(@Valid @RequestBody GameDto gameDto) {
        Game game=gameMapper.fromGameDto(gameDto);
        return gameMapper.toGameDto(gameService.createGame(game));
    }

    @PutMapping("/{id}")
    public GameDto updateGame(@PathVariable UUID id,
                               @Valid @RequestBody GameDto gameDto) {
        Game game=gameMapper.fromGameDto(gameDto);
        return gameMapper.toGameDto(gameService.updateGame(id,game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
}

