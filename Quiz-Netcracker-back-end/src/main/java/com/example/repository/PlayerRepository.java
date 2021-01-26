package com.example.repository;

import com.example.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Optional<Player> findPlayerByEmailAndName(String email, String name);
    Optional<Player> findById(UUID playerId);
    Player getPlayerById(UUID id);
    Player getPlayerByUserId(UUID id);
}
