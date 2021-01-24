package com.example.service.mapper;

import com.example.dto.UserDto;
import com.example.model.Game;
import com.example.model.User;
import com.example.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper implements Mapper<User, UserDto> {
    private final GameService gameService;

    @Autowired
    public UserMapper(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public UserDto toDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setId(entity.getId());
        userDto.setMail(entity.getMail());
        userDto.setLogin(entity.getLogin());
        userDto.setPassword(entity.getPassword());
        userDto.setRole(entity.getRole());
        userDto.setPlayer(entity.getPlayer().getId());
//        userDto.setGames(entity.getGame()
//                .stream()
//                .map(Game::getId)
//                .collect(Collectors.toSet()));
        return userDto;
    }

    @Override
    public User toEntity(UserDto dto) {
//        Set<Game> games = dto.getGames()
//                .stream()
//                .map(gameService::findGameById)
//                .collect(Collectors.toSet());
        User user = new User();
        user.setId(dto.getId());
        user.setMail(dto.getMail());
        user.setLogin(dto.getLogin());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getPassword());
    //    user.setPlayer(dto.getPlayer().);
        return user;
    }

    @Override
    public UserDto toShortDto(User entity) {
        UserDto userDto = new UserDto();
        userDto.setLogin(entity.getLogin());
        userDto.setPassword(entity.getPassword());
        return userDto;
    }
}
