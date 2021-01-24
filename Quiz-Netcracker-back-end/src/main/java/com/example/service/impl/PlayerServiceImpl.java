package com.example.service.impl;

import com.example.model.Player;
import com.example.repository.PlayerRepository;
import com.example.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findPlayer(String property) {
        return playerRepository.findPlayerByName(property).get();
    }

    @Override
    public Player findPlayerById(UUID id) {
        return playerRepository.findById(id).get();
    }

}
