package com.example.controller;

import com.example.dto.GameDto;
import com.example.model.Game;
import com.example.service.interfaces.GameService;
import com.example.service.mapper.Mapper;
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
    public final Mapper mapper;

    public GameController(GameService gameService, Mapper mapper) {
        this.gameService = gameService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable UUID id) {
        return mapper.toShortGameDto(gameService.findGameById(id));
    }

    @GetMapping("/{name}")
    public GameDto getGameByName(@PathVariable String name) {
        return mapper.toGameDto(gameService.findGameByName(name));
    }

    @GetMapping("/all")
    public List<GameDto> getGames() {
        return gameService.findAllGames().stream()
                .map(mapper:: toShortGameDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/game")
    public GameDto createGame(@Valid @RequestBody GameDto gameDto) {
        Game game= mapper.fromGameDto(gameDto);
        return mapper.toGameDto(gameService.createGame(game));
    }

    @PutMapping("/{id}")
    public GameDto updateGame(@PathVariable UUID id,
                               @Valid @RequestBody GameDto gameDto) {
        Game game= mapper.fromGameDto(gameDto);
        return mapper.toGameDto(gameService.updateGame(id,game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
}

