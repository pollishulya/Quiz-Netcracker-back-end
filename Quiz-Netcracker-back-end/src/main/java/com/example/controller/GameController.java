package com.example.controller;

import com.example.model.Game;
import com.example.service.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = {"http://localhost:4200"})
public class GameController {
    public final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{id}")
    public Game getGame(@PathVariable UUID id) {
        return gameService.findGameById(id);
    }

    @GetMapping("/{name}")
    public Game getGameByName(@PathVariable String name) {
        return gameService.findGameByName(name);
    }

    @GetMapping("/all")
    public List<Game> getGames() {
        return gameService.findAllGames();
    }

    @PostMapping("/answer")
    public Game createGame(@Valid @RequestBody Game game) {
        return gameService.createGame(game);
    }

    @PutMapping("/{id}")
    public Game updateGame(@PathVariable UUID id,
                               @Valid @RequestBody Game game) {
        return gameService.updateGame(id,game);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
}

