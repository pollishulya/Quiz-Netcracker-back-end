package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Player;
import com.example.model.User;
import com.example.repository.PlayerRepository;
import com.example.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public List<Player> findAllPlayer() {
        return playerRepository.findAll();
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Player updatePlayer(UUID playerId, User playerRequest) {
        return playerRepository.findById(playerId).map(player -> {
            return playerRepository.save(player);
        }).orElseThrow(() -> new ResourceNotFoundException("Player not found with id " + playerId));
    }

    @Override
    public void deletePlayer(UUID playerId) {
        playerRepository.deleteById(playerId);
    }

    @Override
    public Player getPlayerById(UUID playerId) {
        return playerRepository.findPlayerById(playerId);
    }
}
