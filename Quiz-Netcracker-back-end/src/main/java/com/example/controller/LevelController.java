package com.example.controller;

import com.example.model.Level;
import com.example.service.LevelService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
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
