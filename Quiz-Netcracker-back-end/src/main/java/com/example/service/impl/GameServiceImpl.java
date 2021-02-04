package com.example.service.impl;

import com.example.dto.GameDto;
import com.example.dto.GameFilter;
import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Game;
import com.example.model.Question;
import com.example.repository.GameRepository;
import com.example.service.interfaces.GameAccessService;
import com.example.service.interfaces.GameService;
import com.example.util.QPredicates;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.example.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final QuestionService questionService;
    private final MessageSource messageSource;
    private final EntityManager entityManager;
    private final GameAccessService gameAccessService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           QuestionService questionService,
                           MessageSource messageSource,
                           EntityManager entityManager) {
    public GameServiceImpl(GameRepository gameRepository, QuestionService questionService, MessageSource messageSource, GameAccessService gameAccessService) {
        this.gameRepository = gameRepository;
        this.questionService = questionService;
        this.messageSource = messageSource;
        this.entityManager = entityManager;
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


    @Override
    public List<Game> findByFilter(GameDto dto) {

        GameFilter filter = GameFilter.builder();

        Predicate predicate = QPredicates.builder()
                .add(filter.getTitle(), Game.title::containsIgnoreCase)
                .add(filter.getDescripton(), Game.description::containsIgnoreCase)
                .add(filter.getViews(), Game.views::containsIgnoreCase)
                .add(filter.getRatingCount(), Game.ratingCount::containsIgnoreCase)
                .add(filter.getAverageRating(), Game.averageRating::containsIgnoreCase)
                .buildAnd();
        Iterable<Game> result = GameRepository.findByFilter(predicate);

        return new result;
    }


    @Override
    public List<Game> searchGamesByTitle(String title) {
        return gameRepository.findAllByTitleContaining(title);
    }
    @Override
    public List<Game> findGamesByCategory(String category) {
        return gameRepository.findAllByGameCategory(category);
    }
    @Override
    public List<Game> findAllGamesFilteredByTitle(){
        return gameRepository.findAllByOrderByTitle();
    }
    @Override
    public List<Game> findAllGamesFilteredByRating(){
        return gameRepository.findAllByOrderByAverageRating();
    }
    @Override
    public List<Game> findAllGamesFilteredByViews(){
        return gameRepository.findAllByOrderByViews();
    }

//    @Override
//    public Game updateGameViews(UUID id, Game game) {
//
//        return gameRepository.findById(id).map(game->{
//            game.
//            return gameRepository.save(game);
//        }
//    }

//    @Override
//    public Game updateGameRating(UUID id, Game game) {
//        UUID[] args = new UUID[]{ id };
//        return gameRepository.findById(id).map(game->{
//            game.setPhoto(game.getPhoto());
//
//            return gameRepository.save(game);
//        }).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
//                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
//
//    }

    @Override
    public List<Game> findGameByPlayerId(UUID playerId) {
        return gameRepository.findGamesByPlayerId(playerId);
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

            game.setGameCategory(gameReq.getGameCategory());
            game.setAverageRating(game.getAverageRating());
            game.setRatingCount(gameReq.getRatingCount());
            game.setViews(gameReq.getViews());

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