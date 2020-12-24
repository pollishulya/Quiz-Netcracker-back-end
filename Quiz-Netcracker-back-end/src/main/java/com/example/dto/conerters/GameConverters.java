package com.example.dto.conerters;

import com.example.dto.GameDto;
import com.example.model.Game;

public class GameConverters {
    public static GameDto convert(Game game) {
        GameDto gameDto = null;
        if(game != null){
            gameDto = new GameDto();
            gameDto.setDescription(game.getDescription());
            gameDto.setName(game.getName());
            gameDto.setId(game.getId());
            gameDto.setQuestionsSet(game.getQuestionsSet());
        }
        return gameDto;
    }

    public static Game convert(GameDto gameDto){
        Game game = null;
        if(gameDto != null){
            game = new Game();
            game.setDescription(gameDto.getDescription());
            game.setName(gameDto.getName());
            game.setId(gameDto.getId());
            game.setQuestionsSet(gameDto.getQuestionsSet());
        }
        return game;
    }
}
