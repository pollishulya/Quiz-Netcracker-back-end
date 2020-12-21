package com.example.repository;

import com.example.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game,UUID> {
    Game findGameById(UUID id);
    Game findGameByName(String name);
}