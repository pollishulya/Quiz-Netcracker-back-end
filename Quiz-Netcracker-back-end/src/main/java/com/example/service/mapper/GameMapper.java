package com.example.service.mapper;

import com.example.dto.GameDto;
import com.example.model.Game;
import com.example.model.User;
import com.example.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameMapper implements Mapper<Game, GameDto> {
    private final UserService userService;

    @Autowired
    public GameMapper(UserService userService) {
        this.userService = userService;
    }

    @Override
    public GameDto toDto(Game entity) {
        GameDto gameDto = new GameDto();
        gameDto.setId(entity.getId());
        gameDto.setTitle(entity.getTitle());
        gameDto.setDescription(entity.getDescription());
 //       gameDto.setUser(entity.getUser().getId());
        return gameDto;
    }

    @Override
    public Game toEntity(GameDto dto) {
        User user = userService.getUserById(dto.getUser());
        Game game = new Game();
        game.setId(dto.getId());
        game.setDescription(dto.getDescription());
        game.setTitle(dto.getTitle());
        game.setUser(user);
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
