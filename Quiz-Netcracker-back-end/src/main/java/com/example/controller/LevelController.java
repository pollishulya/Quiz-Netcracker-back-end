package com.example.controller;

import com.example.model.Level;
import com.example.service.LevelService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class LevelController {
    private final LevelService levelService;

    @Autowired
    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping("/levels")
    public CollectionWrapper<Level> getLevels() {
        return new CollectionWrapper<>(levelService.findAll());
    }

    @GetMapping("/levels/{levelId}")
    public Optional<Level> getLevel(@PathVariable UUID levelId) {
        return levelService.findById(levelId);
    }

    @PostMapping("/levels")
    public Level createLevel(@Valid @RequestBody Level level) {
        return levelService.save(level);
    }

    @PutMapping("/levels/{levelId}")
    public Level updateLevel(@PathVariable UUID levelId,
                             @Valid @RequestBody Level level) {
        return levelService.update(levelId, level);
    }

    @DeleteMapping("/levels/{levelId}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID levelId) {
        levelService.delete(levelId);
        return ResponseEntity.ok().build();
    }
}
