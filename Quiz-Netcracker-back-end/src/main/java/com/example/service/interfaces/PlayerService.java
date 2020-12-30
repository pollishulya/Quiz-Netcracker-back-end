package com.example.service.interfaces;

import com.example.model.Player;
import com.example.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public interface PlayerService {
    List<Player> findAllPlayer();
    Player savePlayer(Player player);
    Player updatePlayer(UUID playerId, User playerRequest);
    void deletePlayer(UUID playerId);

    Player getPlayerById(UUID playerId);


}
