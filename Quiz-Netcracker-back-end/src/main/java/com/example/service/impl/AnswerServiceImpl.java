package com.example.service.impl;

import com.example.dto.GameMessage;
import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.*;
import com.example.repository.AnswerRepository;
import com.example.service.interfaces.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final MessageSource messageSource;
    private final PlayerService playerService;
    private final GameRoomService gameRoomService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final StatisticsService statisticsService;
    private final QuestionService questionService;

    public AnswerServiceImpl(AnswerRepository answerRepository, MessageSource messageSource,
                             PlayerService playerService, GameRoomService gameRoomService,
                             SimpMessagingTemplate simpMessagingTemplate, StatisticsService statisticsService,
                             QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.messageSource = messageSource;
        this.playerService = playerService;
        this.gameRoomService = gameRoomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.statisticsService = statisticsService;
        this.questionService = questionService;
    }

    @Override
    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(UUID id) {
        try {
            answerRepository.deleteById(id);
        } catch (RuntimeException exception) {
            UUID[] args = new UUID[]{id};
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Answer updateAnswer(UUID id, Answer answerReq) {
        if (id == null) {
            throw new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                    messageSource.getMessage("message.ResourceNotFound", new Object[]{null}, LocaleContextHolder.getLocale()));
        }
        UUID[] args = new UUID[]{id};
        return answerRepository.findById(id).map(answer -> {
            answer.setTitle(answerReq.getTitle());
            answer.setRight(answerReq.getRight());
            return answerRepository.save(answer);
        }).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public Answer getAnswerById(UUID id) {
        if (id == null) {
            throw new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                    messageSource.getMessage("message.ResourceNotFound", new Object[]{null}, LocaleContextHolder.getLocale()));
        }
        UUID[] args = new UUID[]{id};
        return answerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public List<Answer> getALL() {
        return answerRepository.findAll();
    }

    @Override
    public Answer saveStatisticsAndReturnAnswer(UUID questionId, String answerId, UUID playerId,
                                                UUID gameRoomId, int numberAnswer) throws JsonProcessingException {
        Question question = questionService.getQuestionById(questionId);
        if (answerId.equals("null")) {
            Player player = playerService.findPlayerById(playerId);
            Statistics statistics = new Statistics();
            statistics.setQuestion(question);
            statistics.setAnswer(null);
            statistics.setPlayer(player);
            statisticsService.save(statistics);
            return null;
        }
        Answer answer = answerRepository.findAnswerById(UUID.fromString(answerId));
        Player player = playerService.findPlayerById(playerId);
        GameMessage gameMessage = new GameMessage(answer.getRight(), playerId, numberAnswer);
        GameRoom gameRoom = gameRoomService.findById(gameRoomId);
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player fielder : gameRoom.getPlayers()) {
            if (!player.getId().equals(fielder.getId())) {
                simpMessagingTemplate.convertAndSend("/topic/game/" + fielder.getId(), objectMapper.writeValueAsString(gameMessage));
            }
        }

        Statistics statistics = new Statistics();
        statistics.setQuestion(question);
        statistics.setAnswer(answer);
        statistics.setPlayer(player);
        statisticsService.save(statistics);
        return answer;
    }
}
