package com.example.service.mapper;

import com.example.dto.GameDto;
import com.example.model.Game;
import com.example.model.Question;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GameMapper {
    public GameDto toGameDto(final Game game){
        return GameDto.builder()
                .id(game.getId())
                .description(game.getDescription())
                .name(game.getName())
                .user(game.getUser())
                .questions(game.getQuestionsSet())
                .build();
    }

    public  Game fromGameDto(final GameDto gameDto){
        return Game.builder()
                .id(gameDto.getId())
                .description(gameDto.getDescription())
                .name(gameDto.getName())
                .user(gameDto.getUser())
                .questionsSet(gameDto.getQuestions())
                .build();
    }

    public  GameDto toShortGameDto(final Game game){
        return GameDto.builder()
                .name(game.getName())
                .description(game.getDescription())
                .build();
    }
}
