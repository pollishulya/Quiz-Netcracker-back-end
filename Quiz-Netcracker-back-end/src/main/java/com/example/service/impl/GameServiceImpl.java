package com.example.service.impl;

import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Game;
import com.example.model.Question;
import com.example.repository.GameRepository;
import com.example.service.interfaces.GameAccessService;
import com.example.service.interfaces.GameService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.example.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final QuestionService questionService;
    private final MessageSource messageSource;
    private final GameAccessService gameAccessService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, QuestionService questionService, MessageSource messageSource, GameAccessService gameAccessService) {
        this.gameRepository = gameRepository;
        this.questionService = questionService;
        this.messageSource = messageSource;
        this.gameAccessService = gameAccessService;
    }

    @Override
    public Game createGame(Game game) {
        Game game1 = gameRepository.save(game);
        Set<Question> questions = game1.getQuestions()
                .stream()
                .peek(question -> {
                    question.setGame(game1);
                    questionService.setQuestionToAnswers(question);
                })
                .collect(Collectors.toSet());
        game1.getQuestions().clear();
        game1.getQuestions().addAll(questions);
        gameAccessService.createGameAccessByGame(game.getId());
        return game1;
    }

    public List<Game> searchGamesByTitle(String title) {
        return gameRepository.findAllByTitleContaining(title);
    }

    @Override
    public List<Game> findGameByPlayerId(UUID playerId) {
        return gameRepository.findGamesByPlayerId(playerId);
    }

    @Override
    public List<Game> findPublicGames() {
        return gameRepository.findGameByAccess("PUBLIC");
    }

    @Override
    public void deleteGame(UUID id) {
        try {
            gameRepository.deleteById(id);
        }
        catch (RuntimeException exception) {
            UUID[] args = new UUID[]{ id };
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Game updateGame(UUID id, Game gameReq)
    {
        UUID[] args = new UUID[]{ id };
        return gameRepository.findById(id).map(game->{
            game.setTitle(gameReq.getTitle());
            game.setDescription(gameReq.getDescription());
            game.setPhoto(gameReq.getPhoto());
            if (gameReq.getQuestions() != null) {
                Set<Question> questions = gameReq.getQuestions()
                        .stream()
                        .peek(question -> {
                            question.setGame(game);
                            questionService.setQuestionToAnswers(question);
                        }).collect(Collectors.toSet());
                game.getQuestions().clear();
                game.getQuestions().addAll(questions);
            }
            return gameRepository.save(game);
        }).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public Game findGameById(UUID id) {
        UUID[] args = new UUID[]{ id };
        return gameRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public Game findGameByTitle(String title) {
        return gameRepository.findGameByTitle(title);
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }
}