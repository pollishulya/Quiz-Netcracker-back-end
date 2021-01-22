package com.example.service.mapper;

import com.example.dto.GameDto;
import com.example.model.Game;
import com.example.model.Player;
import com.example.model.User;
import com.example.service.interfaces.PlayerService;
import com.example.service.interfaces.UserService;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GameMapper implements Mapper<Game, GameDto> {
    private final UserService userService;
    private final QuestionMapper questionMapper;
    private final PlayerService playerService;

    public GameMapper(UserService userService, QuestionMapper questionMapper, PlayerService playerService) {
        this.userService = userService;
        this.questionMapper = questionMapper;
        this.playerService = playerService;
    }

    @Override
    public GameDto toDto(Game entity) {
        GameDto gameDto = new GameDto();
        gameDto.setId(entity.getId());
        gameDto.setTitle(entity.getTitle());
        gameDto.setDescription(entity.getDescription());
        gameDto.setQuestions(entity.getQuestions()
                .stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toSet()));
        gameDto.setPlayer(entity.getPlayer().getId());
        return gameDto;
    }

    @Override
    public Game toEntity(GameDto dto) {
        Player player = playerService.findPlayerByUserId(dto.getPlayer());
        Game game = new Game();
        game.setId(dto.getId());
        game.setDescription(dto.getDescription());
        game.setTitle(dto.getTitle());
        game.setQuestions(dto.getQuestions()
                .stream()
                .map(questionMapper::toEntity)
                .collect(Collectors.toSet()));
        game.setPlayer(player);
        return game;
    }

    @Override
    public GameDto toShortDto(Game entity) {
        GameDto gameDto = new GameDto();
        gameDto.setTitle(entity.getTitle());
        gameDto.setDescription(entity.getDescription());
        return gameDto;
    }
}
