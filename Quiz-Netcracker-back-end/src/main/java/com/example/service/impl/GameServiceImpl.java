package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Game;
import com.example.model.Question;
import com.example.repository.GameRepository;
import com.example.service.interfaces.GameService;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final QuestionService questionService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository, QuestionService questionService) {
        this.gameRepository = gameRepository;
        this.questionService = questionService;
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
        return game1;
    }


    public List<Game> searchGamesByTitle(String title) {
        return gameRepository.findAllByTitleContaining(title);
    }

    @Override
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }

    @Override
    public Game updateGame(UUID id, Game gameReq)
    {
        return gameRepository.findById(id).map(game->{
            game.setTitle(gameReq.getTitle());
            game.setDescription(gameReq.getDescription());
            game.setPhoto(gameReq.getPhoto());
            if (gameReq.getQuestions() != null) {
//                gameReq.getQuestions()
//                        .stream()
//                        .peek(question -> {
//                            question.setGame(game);
//                            questionService.setQuestionToAnswers(question);
//                        });
                game.getQuestions().clear();
                game.getQuestions().addAll(gameReq.getQuestions());
            }
            return gameRepository.save(game);
        }).orElseThrow(()-> new ResourceNotFoundException("Object not found"));
    }

    @Override
    public Game findGameById(UUID id) {
        return gameRepository.findGameById(id);
    }

    @Override
    public Game findGameByTitle(String title) {
        return gameRepository.findGameByTitle(title);
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll();
    }

    @Override
    public void saveImage(MultipartFile imageFile) throws Exception {
        String folder = "/photos";
        byte[] bytes = imageFile.getBytes();
        Path path = Paths.get(folder + imageFile.getOriginalFilename());
        Files.write(path, bytes);
    }
}