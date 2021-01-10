package com.example.controller;

import com.example.service.interfaces.PlayerService;
import com.example.service.mapper.PlayerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final PlayerMapper mapper;

    @Autowired
    public PlayerController(PlayerService playerService, PlayerMapper mapper) {
        this.playerService = playerService;
        this.mapper = mapper;
    }

    /*@GetMapping("/findAllPlayers")
    public List<PlayerDto> getPlayers() {
        return playerService.findAllPlayer().stream().map(mapper:: toShortDto).collect(Collectors.toList());
    }*/
}
