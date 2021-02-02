package com.example.repository;

import com.example.model.GameAccess;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameAccessRepository extends JpaRepository<GameAccess, UUID> {
    GameAccess findGameAccessByActivationCode(String code);
    GameAccess findGameAccessesByGameIdAndPlayerId(UUID gameId, UUID playerId);
    GameAccess findGameAccessesByPlayer( UUID playerId);
    GameAccess findGameAccessesByPlayerId(UUID playerId);
}
