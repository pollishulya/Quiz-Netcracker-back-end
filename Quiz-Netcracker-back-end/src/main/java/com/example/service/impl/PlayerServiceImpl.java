package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Player;
import com.example.repository.PlayerRepository;
import com.example.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepository playerRepository;
    private final MessageSource messageSource;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, MessageSource messageSource) {
        this.playerRepository = playerRepository;
        this.messageSource = messageSource;
    }


    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player findPlayer(String property) {
        return playerRepository.findPlayerByEmailAndName(property, property).get();
    }

    @Override
    public Player findPlayerById(UUID id) {
        UUID[] args = new UUID[]{ id };
        return playerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public Player findPlayerByUserId(UUID user) {
        return playerRepository.getPlayerByUserId(user);
    }


//    @Override
//    public Player savePlayer(Player player) {
//        return playerRepository.save(player);
//    }
//
//    @Override
//    public Player updatePlayer(UUID playerId, User playerRequest) {
//        return playerRepository.findById(playerId).map(player -> {
//            return playerRepository.save(player);
//        }).orElseThrow(() -> new ResourceNotFoundException("Player not found with id " + playerId));
//    }
//
//    @Override
//    public void deletePlayer(UUID playerId) {
//        playerRepository.deleteById(playerId);
//    }

}
