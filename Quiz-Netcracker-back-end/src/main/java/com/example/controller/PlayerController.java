package com.example.controller;

import com.example.dto.PlayerDto;
import com.example.model.Photo;
import com.example.model.Player;
import com.example.model.User;
import com.example.repository.PlayerRepository;
import com.example.security.LoginModel;
import com.example.service.impl.AmazonClient;
import com.example.service.interfaces.PlayerService;
import com.example.service.mapper.PlayerMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/player")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerRepository playerRepository;
    private final PlayerMapper mapper;
    private final AmazonClient amazonClient;

    public PlayerController(PlayerService playerService, PlayerRepository playerRepository, PlayerMapper mapper, AmazonClient amazonClient) {
        this.playerService = playerService;
        this.playerRepository = playerRepository;
        this.mapper = mapper;
        this.amazonClient = amazonClient;
    }

    @GetMapping("/{userProperty}")
    public PlayerDto getPlayer(@PathVariable String userProperty) {
        return mapper.toDto(playerService.findPlayer(userProperty));
    }

    @GetMapping("/id/{id}")
    public PlayerDto getPlayerById(@PathVariable UUID id) {
        return mapper.toDto(playerService.findPlayerByUserId(id));
    }

    @GetMapping()
    public List<PlayerDto> getAllPlayers() {
        return playerService.findAllPlayers().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @PutMapping("/update/{playerId}")
    public PlayerDto updatePlayer(@PathVariable UUID playerId,
                              @Valid @RequestBody PlayerDto playerDto) {
        Player player= mapper.toEntity(playerDto);
        return mapper.toDto(playerService.updatePlayer(playerId, player));
    }

    @PostMapping("/uploadFile")
    public Photo uploadFile(@RequestPart(value = "file") MultipartFile file) {
        Photo photo = new Photo();
        photo.setPhoto(this.amazonClient.uploadFile(file));
        return photo;
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @PostMapping("/updateFile/{gameId}")
    public PlayerDto updateFile(@RequestPart(value = "url") String fileUrl,
                              @PathVariable UUID gameId) {
        return this.amazonClient.putObjectForPlayer(fileUrl, gameId);
    }

    @PostMapping("/register")
    UUID singUp(@RequestBody LoginModel loginModel/*, HttpServletRequest request*/){
        Player player = new Player(loginModel.getUsername());
        playerService.savePlayer(player);
        return player.getId();
    }
}