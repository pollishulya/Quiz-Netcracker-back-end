package com.example.controller;

import com.example.dto.AnswerDto;
import com.example.exception.ArgumentNotValidException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.*;
import com.example.service.interfaces.AnswerService;
import com.example.service.interfaces.GameRoomService;
import com.example.service.interfaces.PlayerService;
import com.example.service.interfaces.StatisticsService;
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
    private final CustomValidator customValidator;
    private final PlayerService playerService;
    private final StatisticsService statisticsService;
    private final GameRoomService gameRoomService;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageSource messageSource;

    @Autowired
    public AnswerController(AnswerService answerService, AnswerMapper mapper,
                            CustomValidator customValidator, PlayerService playerService,
                            StatisticsService statisticsService, GameRoomService gameRoomService,
                            SimpMessagingTemplate simpMessagingTemplate, MessageSource messageSource) {
        this.answerService = answerService;
        this.mapper = mapper;
        this.customValidator = customValidator;
        this.playerService = playerService;
        this.statisticsService = statisticsService;
        this.gameRoomService = gameRoomService;
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageSource = messageSource;
    }

    @GetMapping("/{answerId}/{playerId}/{gameRoomId}/{numberAnswer}")
    public AnswerDto getAnswer(@PathVariable UUID answerId, @PathVariable UUID playerId,
                               @PathVariable UUID gameRoomId,@PathVariable int numberAnswer) throws JsonProcessingException {
        Answer answer = answerService.getAnswerById(answerId);
        return saveStatistics(answer, answerId, playerId, gameRoomId, numberAnswer);
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
        Map<String, String> propertyViolation = customValidator.validate(answerDto, Create.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.ARGUMENT_NOT_VALID, propertyViolation, messageSource);
        }
        Answer answer = mapper.toEntity(answerDto);
        return mapper.toDto(answerService.createAnswer(answer));
    }

    @PutMapping("/{id}")
    public AnswerDto updateAnswer(@PathVariable UUID id,
                                  @RequestBody AnswerDto answerDto) {
        Map<String, String> propertyViolation = customValidator.validate(answerDto, Update.class);
        if (!propertyViolation.isEmpty()) {
            throw new ArgumentNotValidException(ErrorInfo.ARGUMENT_NOT_VALID, propertyViolation, messageSource);
        }
        Answer answer = mapper.toEntity(answerDto);
        return mapper.toDto(answerService.updateAnswer(id, answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }

    private AnswerDto saveStatistics(Answer answer, UUID answerId, UUID playerId,
                                     UUID gameRoomId, int numberAnswer) throws JsonProcessingException {
        Player player = playerService.findPlayerById(playerId);
        List<Statistics> statisticsList = statisticsService.findStatisticsByPlayerId(player.getId());

        GameMessage gameMessage = new GameMessage(answer.getRight(), playerId, numberAnswer);
        GameRoom gameRoom = gameRoomService.findById(gameRoomId);
        ObjectMapper objectMapper = new ObjectMapper();
        for (Player fielder : gameRoom.getPlayers()) {
            if (!player.getId().equals(fielder.getId())) {
                simpMessagingTemplate.convertAndSend("/topic/game/" + fielder.getId(), objectMapper.writeValueAsString(gameMessage));
            }
        }

        for (Statistics s : statisticsList) {
            if (s.getAnswer().getId().equals(answerId)) {
                return mapper.toDto(answer);
            }
        }
        Statistics statistics = new Statistics();
        statistics.setAnswer(answer);
        statistics.setPlayer(player);
        statisticsService.save(statistics);
        return mapper.toDto(answer);
    }
}
