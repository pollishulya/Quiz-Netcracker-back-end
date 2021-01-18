package com.example.controller;

import com.example.dto.GameDto;
import com.example.exception.ArgumentNotValidException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Game;
import com.example.service.interfaces.GameService;
import com.example.service.mapper.GameMapper;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.example.service.validation.validator.CustomValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/game")
@CrossOrigin(origins = {"http://localhost:4200"})
public class GameController {
    public final GameService gameService;
    public final GameMapper mapper;
    private final CustomValidator customValidator;

    public GameController(GameService gameService,
                          GameMapper mapper,
                          CustomValidator customValidator) {
        this.gameService = gameService;
        this.mapper = mapper;
        this.customValidator = customValidator;
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable UUID id) {
        return mapper.toDto(gameService.findGameById(id));
    }

//    @GetMapping("/{title}")
//    public GameDto getGameByName(@PathVariable String title) {
//        return mapper.toDto(gameService.findGameByName(title));
//    }

    @GetMapping()
    public List<GameDto> getGames() {
        return gameService.findAllGames().stream()
                .map(mapper:: toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public GameDto createGame(@Valid @RequestBody GameDto gameDto) {
        Map<String, String> propertyViolation = customValidator.validate(gameDto, Create.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.VALIDATION_ERROR, propertyViolation);
        }
        Game game= mapper.toEntity(gameDto);
        return mapper.toDto(gameService.createGame(game));
    }

    @PutMapping("/update/{id}")
    public GameDto updateGame(@PathVariable UUID id,
                               @Valid @RequestBody GameDto gameDto) {
        Map<String, String> propertyViolation = customValidator.validate(gameDto, Update.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.VALIDATION_ERROR, propertyViolation);
        }
        Game game= mapper.toEntity(gameDto);
        return mapper.toDto(gameService.updateGame(id,game));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }
}

