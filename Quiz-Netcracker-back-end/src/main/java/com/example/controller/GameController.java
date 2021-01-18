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
    public final GameMapper mapper;

    public GameController(GameService gameService, GameMapper mapper) {
        this.gameService = gameService;
        this.mapper = mapper;
    }

    @GetMapping("/searchByTitle/{title}")
    public List<GameDto> searchGamesByTitle(@PathVariable String title) {
        return gameService.searchGamesByTitle(title).stream()
                .map(mapper:: toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable UUID id) {
        return mapper.toDto(gameService.findGameById(id));
    }

    @GetMapping("/getByTitle/{title}")
    public GameDto getGameByTitle(@PathVariable String title) {
       return mapper.toDto(gameService.findGameByTitle(title));
    }

    @GetMapping()
    public List<GameDto> getGames() {
        return gameService.findAllGames().stream()
                .map(mapper:: toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public GameDto createGame(@Valid @RequestBody GameDto gameDto) {
        Game game= mapper.toEntity(gameDto);
        return mapper.toDto(gameService.createGame(game));
    }

    @PutMapping("/update/{id}")
    public GameDto updateGame(@PathVariable UUID id,
                               @Valid @RequestBody GameDto gameDto) {
        Game game= mapper.toEntity(gameDto);
        return mapper.toDto(gameService.updateGame(id,game));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
}

