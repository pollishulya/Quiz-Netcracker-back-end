package com.example.service.impl;

import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.*;
import com.example.repository.GameRepository;
import com.example.service.interfaces.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.CollectionExpression;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.QGame;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final QuestionService questionService;
    private final MessageSource messageSource;
    private final GameAccessService gameAccessService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,
                           QuestionService questionService,
                           MessageSource messageSource,
                           EntityManager entityManager,
                           GameAccessService gameAccessService) {
        this.gameAccessService = gameAccessService;
        this.gameRepository = gameRepository;
        this.questionService = questionService;
        this.messageSource = messageSource;
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
    public List<Game> findByFilter(GameFilterRequest request) {

//        GameFilter filter = GameFilter.builder()
//                .build();
//
//        Predicate predicate = QPredicates.builder()
//                .add(filter.getTitle(), request.title::containsIgnoreCase)
//                .add(filter.getDescripton(), request.description::containsIgnoreCase)
//                .add(filter.getViews(), request.views::containsIgnoreCase)
//                .add(filter.getRatingCount(), request.ratingCount::containsIgnoreCase)
//                .add(filter.getAverageRating(), request.averageRating::containsIgnoreCase)
//                .buildAnd();
//        Iterable<Game> result = GameRepository.findByFilter(predicate);

        BooleanBuilder predicate = new BooleanBuilder();

        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            predicate.and(QGame.game.title.in(request.getTitle()));
        }
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            predicate.and(QGame.game.description.in(request.getDescription()));
        }
        if (request.getViews() != null) {
            predicate.and(QGame.game.views.in(request.getViews()));
        }
        if (request.getRatingCount() != null) {
            predicate.and(QGame.game.ratingCount.in(request.getRatingCount()));
        }
        if (request.getAverageRating() != null) {
            predicate.and(QGame.game.averageRating.in(request.getAverageRating()));
        }
        if (request.getGameCategory() != null) {
            predicate.and(QGame.game.gameCategory.title.in((CollectionExpression<?, ? extends String>) request.getGameCategory()));
        }



        return (List<Game>) gameRepository.findAll(predicate);

    }

    @Override
    public void saveImage(MultipartFile imageFile) throws Exception {

    }


    @Override
    public List<Game> searchGamesByTitle(String title) {
        return gameRepository.findAllByTitleContaining(title);
    }
    @Override
    public List<Game> findGamesByCategory(UUID gameCategoryId) {
        return gameRepository.findAllByGameCategoryId(gameCategoryId);
    }
    @Override
    public List<Game> findAllGamesFilteredByTitle(){
        return gameRepository.findByOrderByTitleAsc();
    }

    @Override
    public List<Game> findTopViewedGames() {
        return gameRepository.findTop3ByOrderByViewsDesc();
    }

    @Override
    public List<Game> findAllGamesFilteredByRating(){
        return gameRepository.findAllByOrderByAverageRating();
    }
    @Override
    public List<Game> findAllGamesFilteredByViews(){
        //return gameRepository.findByOrderByViewsDesc();
        return gameRepository.findAll(Sort.by(Sort.Direction.DESC, "views"));
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
    public List<Game> findPublicGames() {
        return gameRepository.findGameByAccess("PUBLIC");
    }

    @Override
    public void deleteGame(UUID id) {
        try {
            List<GameAccess> gameAccesses=gameAccessService.getGameAccessesByGameId(id);
            if(gameAccesses==null/*&&gameRooms==null*/){
                gameRepository.deleteById(id);
            }
           else {
                gameAccessService.getGameAccessesByGameId(id)
                        .stream()
                        .peek(gameAccess -> {
                           gameAccessService.delete(gameAccess.getId());
                        })
                        .collect(Collectors.toList());
//                gameRoomService.findByGameId(id)
//                        .stream()
//                        .peek(gameRoom -> {
//                            gameRoomService.delete(gameRoom.getId());
//                        })
//                        .collect(Collectors.toList());

               gameRepository.deleteById(id);
            }


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
            game.setAccess(gameReq.getAccess());
             gameAccessService.updateGameAccess(game);
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