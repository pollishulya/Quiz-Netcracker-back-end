package com.example.controller;

import com.example.dto.AnswerDto;
import com.example.exception.ArgumentNotValidException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Answer;
import com.example.service.interfaces.AnswerService;
import com.example.service.mapper.AnswerMapper;
import com.example.service.validation.group.Create;
import com.example.service.validation.group.Update;
import com.example.service.validation.validator.CustomValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
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
    private final MessageSource messageSource;

    @Autowired
    public AnswerController(AnswerService answerService, AnswerMapper mapper,
                            CustomValidator customValidator, MessageSource messageSource) {
        this.answerService = answerService;
        this.mapper = mapper;
        this.customValidator = customValidator;
        this.messageSource = messageSource;
    }

    @GetMapping("/{questionId}/{answerId}/{playerId}/{gameRoomId}/{numberAnswer}")
    public AnswerDto getAnswer(@PathVariable UUID questionId, @PathVariable String answerId, @PathVariable UUID playerId,
                               @PathVariable UUID gameRoomId, @PathVariable int numberAnswer) throws JsonProcessingException {
        return mapper.toDto(answerService.saveStatisticsAndReturnAnswer(questionId, answerId, playerId,
                gameRoomId, numberAnswer));
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
}
