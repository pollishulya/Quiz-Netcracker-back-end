package com.example.controller;

import com.example.dto.PlayerDto;
import com.example.dto.UserDto;
import com.example.service.interfaces.PlayerService;
import com.example.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/players")
public class PlayerController {

    private final PlayerService playerService;
    private final Mapper mapper;

    @Autowired
    public PlayerController(PlayerService playerService, Mapper mapper) {
        this.playerService = playerService;
        this.mapper = mapper;
    }

    /*@GetMapping("/findAllPlayers")
    public List<PlayerDto> getPlayers() {
        return playerService.findAllPlayer().stream().map(mapper:: toShortDto).collect(Collectors.toList());
    }*/
}
