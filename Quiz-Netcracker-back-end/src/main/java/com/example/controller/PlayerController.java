package com.example.controller;

import com.example.dto.PlayerDto;
import com.example.exception.InvalidEmailException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Photo;
import com.example.model.Player;
import com.example.service.impl.AmazonClient;
import com.example.service.interfaces.PlayerService;
import com.example.service.mapper.PlayerMapper;
import com.example.service.validation.validator.CustomValidator;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper mapper;
    private final AmazonClient amazonClient;
    private final CustomValidator customValidator;
    private final MessageSource messageSource;

    public PlayerController(PlayerService playerService, PlayerMapper mapper,
                            AmazonClient amazonClient, CustomValidator customValidator,
                            MessageSource messageSource) {
        this.playerService = playerService;
        this.mapper = mapper;
        this.amazonClient = amazonClient;
        this.customValidator = customValidator;
        this.messageSource = messageSource;
    }

    @GetMapping("/id/{id}")
    public PlayerDto getPlayerById(@PathVariable UUID id) {
        return mapper.toDto(playerService.findPlayerById(id));
    }

    @GetMapping("/userId/{id}")
    public PlayerDto getPlayerByUserId(@PathVariable UUID id) {
        return mapper.toDto(playerService.findPlayerByUserId(id));
    }

    @GetMapping("/guest/{name}")
    public PlayerDto getGuest(@PathVariable String name) throws InterruptedException {
        Thread.sleep(500);
        return mapper.toShortDto(this.playerService.findGuest(name));
    }

    @GetMapping()
    public List<PlayerDto> getAllPlayers() {
        return playerService.findAllPlayers().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping("/update/{playerId}")
    public PlayerDto updatePlayer(@PathVariable UUID playerId, @RequestBody PlayerDto playerDto) {
        if (!customValidator.validateByRegexp(playerDto.getEmail(), "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$")) {
            throw new InvalidEmailException(ErrorInfo.INVALID_EMAIL_ERROR,
                    messageSource.getMessage("message.InvalidEmail", null, LocaleContextHolder.getLocale()));
        }
        Player player = mapper.toEntity(playerDto);
        return mapper.toDto(playerService.updatePlayer(playerId, player));
    }

    @PostMapping("/uploadFile")
    public Photo uploadFile(@RequestPart(value = "file") MultipartFile file) {
        return new Photo(this.amazonClient.uploadFile(file));
    }

    @PostMapping("/register-guest")
    Player singUpGuest(@RequestBody String login) {
        return playerService.savePlayer(new Player(login));
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGame(@PathVariable UUID id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/updateFile/{gameId}")
    public PlayerDto updateFile(@RequestPart(value = "url") String fileUrl,
                                @PathVariable UUID gameId) {
        return this.amazonClient.putObjectForPlayer(fileUrl, gameId);
    }

}