package com.example.controller;

import com.example.dto.LevelDto;
import com.example.model.Level;
import com.example.service.interfaces.LevelService;
import com.example.service.mapper.LevelMapper;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class LevelController {
    private final LevelService levelService;
    private final LevelMapper levelMapper;
    @Autowired
    public LevelController(LevelService levelService, LevelMapper levelMapper) {
        this.levelService = levelService;
        this.levelMapper = levelMapper;
    }

    @GetMapping("/levels")
    public List<LevelDto> getLevels() {
        return levelService.findAll().stream()
                .map(levelMapper :: toShortLevelDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/levels/{levelId}")
    public LevelDto getLevel(@PathVariable UUID levelId) {
        return levelMapper.toLevelDto(levelService.findLevelById(levelId));
    }

    @PostMapping("/levels")
    public LevelDto createLevel(@Valid @RequestBody LevelDto levelDto) {
        Level level=levelMapper.fromLevelDto(levelDto);
        return levelMapper.toLevelDto(levelService.save(level));
    }

    @PutMapping("/levels/{levelId}")
    public LevelDto updateLevel(@PathVariable UUID levelId,
                             @Valid @RequestBody LevelDto levelDto) {
        Level level=levelMapper.fromLevelDto(levelDto);
        return levelMapper.toLevelDto(levelService.update(levelId,level));
    }

    @DeleteMapping("/levels/{levelId}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID levelId) {
        levelService.delete(levelId);
        return ResponseEntity.ok().build();
    }
}
