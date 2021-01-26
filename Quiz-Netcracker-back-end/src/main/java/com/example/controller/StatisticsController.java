package com.example.controller;

import com.example.dto.StatisticsDto;
import com.example.dto.GameStatisticsDto;
import com.example.service.interfaces.StatisticsService;
import com.example.service.mapper.StatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/statistics")
@CrossOrigin(origins = {"http://localhost:4200"})
public class StatisticsController {
    private final StatisticsService statisticsService;
    private final StatisticsMapper mapper;

    @Autowired
    public StatisticsController(StatisticsService statisticsService, StatisticsMapper mapper) {
        this.statisticsService = statisticsService;
        this.mapper = mapper;
    }

    @GetMapping("/{userId}")
    public List<StatisticsDto> getStatistics(@PathVariable UUID userId) {
        return statisticsService.findStatisticsByPlayerId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{userId}/{gameId}")
    public List<GameStatisticsDto> getStatisticsByPlayerAndGame(@PathVariable UUID gameId, @PathVariable UUID userId) {
        return statisticsService.findStatisticsByPlayerIdAndGameId(gameId,userId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        statisticsService.delete(id);
        return ResponseEntity.ok().build();
    }
}
