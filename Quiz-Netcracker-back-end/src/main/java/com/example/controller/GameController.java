package com.example.controller;

import com.example.dto.GameDto;
import com.example.exception.ArgumentNotValidException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Game;
import com.example.model.Response;
import com.example.repository.GamePageRepository;
import com.example.service.impl.AmazonClient;
import com.example.service.interfaces.GamePageService;
import com.example.service.interfaces.GameService;
import com.example.service.mapper.GameMapper;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.example.service.validation.validator.CustomValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public final GamePageService gamePageService;
    public final GameMapper mapper;
    private final CustomValidator customValidator;
    private final AmazonClient amazonClient;

    @Autowired
    public GameController(GameService gameService, GamePageService gamePageService, GameMapper mapper,
                          CustomValidator customValidator, AmazonClient amazonClient) {
        this.gameService = gameService;
        this.gamePageService = gamePageService;
        this.mapper = mapper;
        this.customValidator = customValidator;
        this.amazonClient = amazonClient;
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

    @GetMapping("/pageable")
    public Response list(@RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "3") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Game> pageResult = gamePageService.findAll(pageRequest);
        return new Response(pageResult.getContent(), pageResult.getTotalPages(),
                pageResult.getNumber(), pageResult.getSize());
    }
    @GetMapping("/getByTitle/{title}")
    public GameDto getGameByTitle(@PathVariable String title) {
       return mapper.toDto(gameService.findGameByTitle(title));
    }

    @GetMapping()
    public List<GameDto> getGames() {
        return gameService.findAllGames().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/save")
    public GameDto createGame(@Valid @RequestBody GameDto gameDto) {
        Map<String, String> propertyViolation = customValidator.validate(gameDto, Create.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.VALIDATION_ERROR, propertyViolation);
        }
        Game game = mapper.toEntity(gameDto);
        return mapper.toDto(gameService.createGame(game));
    }

    @PutMapping("/update/{id}")
    public GameDto updateGame(@PathVariable UUID id,
                              @Valid @RequestBody GameDto gameDto) {
        Map<String, String> propertyViolation = customValidator.validate(gameDto, Update.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.VALIDATION_ERROR, propertyViolation);
        }
        Game game = mapper.toEntity(gameDto);
        return mapper.toDto(gameService.updateGame(id, game));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        gameService.deleteGame(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @PostMapping("/updateFile/{gameId}")
    public GameDto updateFile(@RequestPart(value = "url") String fileUrl,
                             @PathVariable UUID gameId) {
        return this.amazonClient.putObject(fileUrl, gameId);
    }
}

