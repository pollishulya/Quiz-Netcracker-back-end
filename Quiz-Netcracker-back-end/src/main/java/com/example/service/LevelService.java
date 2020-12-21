package com.example.service;

import com.example.model.Level;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LevelService {
    List<Level> findAll();
    Optional<Level> findById(UUID id);
    Level save(Level level);
    Level update(UUID id, Level level);
    void delete(UUID id);
}
