package com.example.controller;

import com.example.dto.AnswerDto;
import com.example.exception.ArgumentNotValidException;
import com.example.model.Answer;
import com.example.service.interfaces.AnswerService;
import com.example.service.mapper.AnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.service.validation.group.CreateValidationGroup;
import com.example.service.validation.group.UpdateValidationGroup;
import com.example.service.validation.validator.impl.BaseCustomValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answer")
@CrossOrigin(origins = {"http://localhost:4200"})

public class AnswerController {
    public final AnswerService answerService;
    private final AnswerMapper mapper;
    private final BaseCustomValidator baseCustomValidator;

    @Autowired
    public AnswerController(AnswerService answerService,
                            AnswerMapper mapper,
                            BaseCustomValidator baseCustomValidator) {
        this.answerService = answerService;
        this.mapper = mapper;
        this.baseCustomValidator = baseCustomValidator;
    }

    @GetMapping("/{id}")
    public AnswerDto getAnswer(@PathVariable UUID id) {
        return mapper.toDto(answerService.getAnswerById(id));
    }

    @GetMapping("/all")
    public List<AnswerDto> getAnswer() {
        return answerService.getALL().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/answer")
    public AnswerDto createAnswer(@RequestBody AnswerDto answerDto) {
        String errorMessages = baseCustomValidator.validate(answerDto, CreateValidationGroup.class);
        if (!errorMessages.isEmpty()) {
            throw new ArgumentNotValidException(errorMessages);
        }
        Answer answer = mapper.toEntity(answerDto);
        return mapper.toDto(answerService.createAnswer(answer));
    }

    @PutMapping("/{id}")
    public AnswerDto updateAnswer(@PathVariable UUID id,
                                  @RequestBody AnswerDto answerDto) {
        String errorMessages = baseCustomValidator.validate(answerDto, UpdateValidationGroup.class);
        if (!errorMessages.isEmpty()) {
            throw new ArgumentNotValidException(errorMessages);
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
