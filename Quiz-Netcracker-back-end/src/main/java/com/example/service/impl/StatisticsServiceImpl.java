package com.example.service.impl;

import com.example.model.Answer;
import com.example.model.Question;
import com.example.dto.GameStatisticsDto;
import com.example.model.Statistics;
import com.example.repository.StatisticsRepository;
import com.example.service.interfaces.GameService;
import com.example.service.interfaces.PlayerService;
import com.example.service.interfaces.QuestionService;
import com.example.service.interfaces.StatisticsService;
import com.example.service.mapper.AnswerMapper;
import com.example.service.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final GameService gameService;
    private final PlayerService playerService;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, GameService gameService, PlayerService playerService, QuestionService questionService, QuestionMapper questionMapper, AnswerMapper answerMapper) {
        this.statisticsRepository = statisticsRepository;
        this.gameService = gameService;
        this.playerService = playerService;
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
    }


    @Override
    public List<Statistics> findStatisticsByPlayerId(UUID id) {
        return statisticsRepository.getStatisticsByPlayerId(id);
    }

    @Override
    public List<GameStatisticsDto> findStatisticsByPlayerIdAndGameId(UUID gameId, UUID userId) {
        List<GameStatisticsDto> gameStatisticsDtos = new ArrayList<>();

        Set<Question> questions = gameService.findGameById(gameId).getQuestions();

        UUID playerId = playerService.findPlayerByUserId(userId).getId();

        for (Statistics s : findStatisticsByPlayerId(playerId)) {
            if (s.getAnswer().getQuestion().getGame().getId().equals(gameId)) {
                for (Question question : questions) {
                    if (s.getAnswer().getQuestion().getId().equals(question.getId())) {
                        gameStatisticsDtos.add(new GameStatisticsDto(questionMapper.toDto(question),
                                answerMapper.toDto(s.getAnswer()), getPercent(question.getId())));
                    }
                }
            }
        }
        return gameStatisticsDtos;
    }

    @Override
    public Statistics save(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }


    @Override
    public void delete(UUID id) {
        statisticsRepository.deleteById(id);
    }


    public double getPercent(UUID questionId) {
        double number = 0;
        double all = 0;
        Question question = questionService.getQuestionById(questionId);
        List<Statistics> statistics = statisticsRepository.findAll();
        for (Answer answer : question.getAnswersSet()) {
            for (Statistics s : statistics) {
                if (s.getAnswer().equals(answer)) {
                    all++;
                    if(answer.getRight()){
                        number++;
                    }
                }
            }
        }
        return number / all * 100;
    }
}
