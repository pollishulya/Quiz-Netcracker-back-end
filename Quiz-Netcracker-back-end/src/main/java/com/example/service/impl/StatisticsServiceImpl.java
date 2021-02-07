package com.example.service.impl;

import com.example.dto.GameStatisticsDto;
import com.example.exception.DeleteEntityException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Player;
import com.example.model.Question;
import com.example.model.Statistics;
import com.example.repository.StatisticsRepository;
import com.example.service.interfaces.*;
import com.example.service.mapper.AnswerMapper;
import com.example.service.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository statisticsRepository;
    private final GameService gameService;
    private final PlayerService playerService;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final MessageSource messageSource;
    private final UserService userService;

    @Autowired
    public StatisticsServiceImpl(StatisticsRepository statisticsRepository, GameService gameService,
                                 PlayerService playerService, QuestionService questionService,
                                 QuestionMapper questionMapper, AnswerMapper answerMapper,
                                 MessageSource messageSource, UserService userService) {
        this.statisticsRepository = statisticsRepository;
        this.gameService = gameService;
        this.playerService = playerService;
        this.questionService = questionService;
        this.questionMapper = questionMapper;
        this.answerMapper = answerMapper;
        this.messageSource = messageSource;
        this.userService = userService;
    }


    @Override
    public List<Statistics> findStatisticsByPlayerId(UUID id) {
        return statisticsRepository.getStatisticsByPlayerId(id);
    }

    @Override
    public List<GameStatisticsDto> findStatisticsByPlayerIdAndGameId(UUID gameId, UUID id) {
        List<GameStatisticsDto> gameStatisticsDto = new ArrayList<>();

        Set<Question> questions = gameService.findGameById(gameId).getQuestions();

        UUID playerId = playerService.findPlayerById(id).getId();

        for (Statistics s : findStatisticsByPlayerId(playerId)) {
            if (s.getQuestion().getGame().getId().equals(gameId)) {
                for (Question question : questions) {
                    if (s.getQuestion().getId().equals(question.getId())) {
                        if (s.getAnswer() == null) {
                            gameStatisticsDto.add(new GameStatisticsDto(questionMapper.toDto(question),
                                    null, getPercent(question.getId())));
                        } else {
                            gameStatisticsDto.add(new GameStatisticsDto(questionMapper.toDto(question),
                                    answerMapper.toDto(s.getAnswer()), getPercent(question.getId())));
                        }
                    }
                }
            }
        }
        return gameStatisticsDto;
    }

    @Override
    public Statistics save(Statistics statistics) {
        return statisticsRepository.save(statistics);
    }


    @Override
    public void delete(UUID id) {
        try {
            List<Statistics> statistics = statisticsRepository.getStatisticsByPlayerId(id);
            for (Statistics statistic : statistics) {
                statisticsRepository.deleteById(statistic.getId());
            }
        } catch (RuntimeException exception) {
            UUID[] args = new UUID[]{id};
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public double getTotalPercentByPlayerId(UUID uuid) {
        List<Statistics> statisticsByPlayerId = statisticsRepository.getStatisticsByPlayerId(uuid);
        AtomicInteger rightAns = new AtomicInteger(0);
        int totalAns = statisticsByPlayerId.size();
        statisticsByPlayerId
                .forEach(statistics -> {
                    if (statistics.getAnswer() != null && statistics.getAnswer().getRight()) {
                        rightAns.incrementAndGet();
                    }
                });
        return ((double) rightAns.get() * 100) / totalAns;
    }

    @Override
    public Map<String, Double> getTotalPercentAllPlayers() {
        Map<String, Double> map = new HashMap<>();
        userService.findAllUser()
                .forEach(u -> {
                    Player p = playerService.findPlayerByUserId(u.getId());
                    double value = getTotalPercentByPlayerId(p.getId());
                    if (value <= 1 || value >= 0) {
                        map.put(p.getName(), value);
                    }
                });
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public double getPercent(UUID questionId) {
        double number = 0;
        double all = 0;
        Question question = questionService.getQuestionById(questionId);
        List<Statistics> statistics = statisticsRepository.findAll();
        for (Statistics s : statistics) {
            if (s.getQuestion().equals(question)) {
                all++;
                if (s.getAnswer() != null && s.getAnswer().getRight()) {
                    number++;
                }
            }
        }
        return number / all * 100;
    }
}
