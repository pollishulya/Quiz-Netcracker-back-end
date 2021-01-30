package com.example.service.interfaces;

import com.example.model.Player;
import com.example.model.User;

import java.util.List;
import java.util.UUID;

public interface PlayerService {
    Player updatePlayer(UUID playerId, Player playerRequest);
    List<Player> findAllPlayers();
    Player findPlayer(String property);
    Player findPlayerById(UUID id);
    Player findPlayerByUserId(UUID player);
}
