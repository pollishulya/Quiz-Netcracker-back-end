package com.example.controller;

import com.example.dto.AnswerDto;
import com.example.dto.GameMessage;
import com.example.exception.ArgumentNotValidException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.*;
import com.example.service.interfaces.*;
import com.example.service.mapper.AnswerMapper;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.example.service.validation.validator.CustomValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answer")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AnswerController {
    private final AnswerService answerService;
    private final AnswerMapper mapper;
    private final PlayerService playerService;
    private final StatisticsService statisticsService;
    private final GameRoomService gameRoomService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final QuestionService questionService;

    @Autowired
    public AnswerController(AnswerService answerService, AnswerMapper mapper,
                            PlayerService playerService, StatisticsService statisticsService,
                            GameRoomService gameRoomService, SimpMessagingTemplate simpMessagingTemplate,
                            QuestionService questionService) {
        this.answerService = answerService;
        this.mapper = mapper;
        this.playerService = playerService;
        this.statisticsService = statisticsService;
        this.gameRoomService = gameRoomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.questionService = questionService;
    }

    @GetMapping("/{questionId}/{answerId}/{playerId}/{gameRoomId}/{numberAnswer}")
    public AnswerDto getAnswer(@PathVariable UUID questionId, @PathVariable String answerId, @PathVariable UUID playerId,
                               @PathVariable UUID gameRoomId,@PathVariable int numberAnswer) throws JsonProcessingException {
        Answer answer;
        Question question = questionService.getQuestionById(questionId);
        if(answerId.equals("null")){
            Player player = playerService.findPlayerById(playerId);
            Statistics statistics = new Statistics();
            statistics.setQuestion(question);
            statistics.setAnswer(null);
            statistics.setPlayer(player);
            statisticsService.save(statistics);
            return null;
        }
        answer = answerService.getAnswerById(UUID.fromString(answerId));
        return saveStatistics(question, answer, playerId, gameRoomId, numberAnswer);
    }

    @GetMapping("/{id}")
    public AnswerDto getAnswer(@PathVariable UUID id) {
        return mapper.toDto(answerService.getAnswerById(id));
    }

    @GetMapping("/all")
    public List<AnswerDto> getAnswer() {
        return answerService.getALL().stream()
                .map(mapper::toShortDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/answer")
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto) {
        Answer answer = mapper.toEntity(answerDto);
        return mapper.toDto(answerService.createAnswer(answer));
    }

    @PutMapping("/{id}")
    public AnswerDto updateAnswer(@PathVariable UUID id,
                                  @RequestBody AnswerDto answerDto) {
        Answer answer = mapper.toEntity(answerDto);
        return mapper.toDto(answerService.updateAnswer(id, answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }

    private AnswerDto saveStatistics(Question question, Answer answer, UUID playerId,
                                     UUID gameRoomId, int numberAnswer) throws JsonProcessingException {
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
        return mapper.toDto(answer);
    }
}
