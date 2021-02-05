package com.example.repository;

import com.example.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game,UUID> {
    Game findGameById(UUID id);
    Game findGameByTitle(String title);
    List<Game> findAllByTitleContaining(String title);
    List<Game> findGamesByPlayerId(UUID playerId);
    List<Game> findGameByAccess(String access);
}