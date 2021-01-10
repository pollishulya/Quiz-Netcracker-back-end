package com.example.service.mapper;

import com.example.dto.PlayerDto;
import com.example.model.Game;
import com.example.model.Player;
import com.example.service.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PlayerMapper implements Mapper<Player, PlayerDto> {
    private final GameService gameService;

    @Autowired
    public PlayerMapper(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public PlayerDto toDto(Player entity) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(entity.getId());
        playerDto.setGames(entity.getGame()
                .stream()
                .map(Game::getId)
                .collect(Collectors.toSet()));
        return playerDto;
    }

    @Override
    public Player toEntity(PlayerDto dto) {
        Set<Game> games = dto.getGames()
                .stream()
                .map(gameService::findGameById)
                .collect(Collectors.toSet());
        Player player = new Player();
        player.setId(dto.getId());
        player.setGame(games);
        return player;
    }

    // Всего 2 параметра в сущности, пока что не знаю что здесь резонно вернуть
    @Override
    public PlayerDto toShortDto(Player entity) {
        return null;
    }
}
