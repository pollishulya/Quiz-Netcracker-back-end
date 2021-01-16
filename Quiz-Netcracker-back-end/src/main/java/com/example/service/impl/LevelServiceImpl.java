package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Level;
import com.example.repository.LevelRepository;
import com.example.service.interfaces.LevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public List<Level> findAll() {
        return levelRepository.findAll();
    }

    @Override
    public Level findLevelById(UUID id) {
        return levelRepository.findLevelById(id);
    }

    @Override
    public Level save(Level level) {
        return levelRepository.save(level);
    }

    @Override
    public Level update(UUID id, Level levelRequest) {
        return levelRepository.findById(id).map(level -> {
            level.setTitle(levelRequest.getTitle());
            level.setDescription(levelRequest.getDescription());
            return levelRepository.save(level);
        }).orElseThrow(() -> new ResourceNotFoundException("Level not found with id " + id));
    }

    @Override
    public void delete(UUID id) {
        levelRepository.deleteById(id);
    }
}
