package com.example.service.impl;

import com.example.dto.PlayerDto;
import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.GameRoom;
import com.example.model.Player;
import com.example.repository.GameRoomRepository;
import com.example.service.interfaces.GameRoomService;
import com.example.service.interfaces.GameService;
import com.example.service.interfaces.PlayerService;
import com.example.service.mapper.PlayerMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameRoomServiceImpl implements GameRoomService {
    private final GameRoomRepository gameRoomRepository;
    private final PlayerService playerService;
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageSource messageSource;
    private final PlayerMapper playerMapper;

    public GameRoomServiceImpl(GameRoomRepository gameRoomRepository, PlayerService playerService, GameService gameService,
                               SimpMessagingTemplate simpMessagingTemplate, MessageSource messageSource, PlayerMapper playerMapper) {
        this.gameRoomRepository = gameRoomRepository;
        this.playerService = playerService;
        this.gameService = gameService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageSource = messageSource;
        this.playerMapper = playerMapper;
    }

    @Override
    public GameRoom findById(UUID id) {
        UUID[] args = new UUID[]{ id };
        return gameRoomRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public List<GameRoom> findByGameId(UUID id) {
        UUID[] args = new UUID[]{ id };
        return gameRoomRepository.findGameRoomByGameId(id);
    }

    @Override
    public GameRoom save(GameRoom gameRoom) {
        return gameRoomRepository.save(gameRoom);
    }

    @SneakyThrows
    @Override
    public GameRoom findGameRoom(UUID gameId, UUID playerId) {
        Player player = playerService.findPlayerById(playerId);
        List<GameRoom> gameRooms = gameRoomRepository.findGameRoomByGameId(gameId);
        for (GameRoom gameRoom : gameRooms) {
            if (gameRoom.getPlayers().size() < 3) {
                gameRoom.getPlayers().add(player);
                ObjectMapper mapper = new ObjectMapper();
                Set<PlayerDto> dtoSet = new HashSet<>();
                for (Player entity: gameRoom.getPlayers()) {
                    if(entity.getUser() == null){
                        dtoSet.add(playerMapper.toShortDto(entity));
                    } else {
                        dtoSet.add(playerMapper.toDto(entity));
                    }
                }
                for (Player playerInGameRoom : gameRoom.getPlayers()) {
                    if (!playerInGameRoom.getId().equals(playerId)) {
                        simpMessagingTemplate.convertAndSend("/topic/game/" + playerInGameRoom.getId(), mapper.writeValueAsString(dtoSet));
                    }
                }
                return save(gameRoom);
            }
        }
        return save(new GameRoom(gameService.findGameById(gameId), Collections.singleton(player)));
    }

    @Override
    public void delete(UUID id) {
        try {
            gameRoomRepository.deleteById(id);
        }
        catch (RuntimeException exception) {
            UUID[] args = new UUID[]{ id };
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }
}
