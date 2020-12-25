package com.example.controller;

import com.example.dto.LevelDto;
import com.example.model.Level;
import com.example.service.interfaces.LevelService;
import com.example.service.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
public class LevelController {
    private final LevelService levelService;
    private final Mapper mapper;
    @Autowired
    public LevelController(LevelService levelService, Mapper mapper) {
        this.levelService = levelService;
        this.mapper = mapper;
    }

    @GetMapping("/levels")
    public List<LevelDto> getLevels() {
        return levelService.findAll().stream()
                .map(mapper:: toShortLevelDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/levels/{levelId}")
    public LevelDto getLevel(@PathVariable UUID levelId) {
        return mapper.toLevelDto(levelService.findLevelById(levelId));
    }

    @PostMapping("/levels")
    public LevelDto createLevel(@Valid @RequestBody LevelDto levelDto) {
        Level level= mapper.fromLevelDto(levelDto);
        return mapper.toLevelDto(levelService.save(level));
    }

    @PutMapping("/levels/{levelId}")
    public LevelDto updateLevel(@PathVariable UUID levelId,
                             @Valid @RequestBody LevelDto levelDto) {
        Level level= mapper.fromLevelDto(levelDto);
        return mapper.toLevelDto(levelService.update(levelId,level));
    }

    @DeleteMapping("/levels/{levelId}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID levelId) {
        levelService.delete(levelId);
        return ResponseEntity.ok().build();
    }
}
