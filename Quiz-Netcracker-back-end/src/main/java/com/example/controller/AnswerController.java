package com.example.controller;

import com.example.dto.AnswerDto;
import com.example.model.Answer;
import com.example.service.interfaces.AnswerService;
import com.example.service.mapper.AnswerMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/answer")
@CrossOrigin(origins = {"http://localhost:4200"})

public class AnswerController {
    public final AnswerService answerService;
    public final AnswerMapper answerMapper;

    public AnswerController(AnswerService answerService, AnswerMapper answerMapper) {
        this.answerService = answerService;
        this.answerMapper = answerMapper;
    }

    @GetMapping("/{id}")
    public AnswerDto getAnswer(@PathVariable UUID id) {
        return answerMapper.toShortAnswerDto(answerService.getAnswerById(id)); //???тут есть 2 способа, но нужно уточнять
    }

    @GetMapping("/all")
    public List<AnswerDto> getAnswer() {
        return answerService.getALL().stream()
                .map(answerMapper :: toShortAnswerDto)//???????
                .collect(Collectors.toList());
    }

    @PostMapping("/answer")
    public AnswerDto createAnswer(@Valid @RequestBody AnswerDto answerDto) {
        Answer answer=answerMapper.fromAnswerDto(answerDto);
        return answerMapper.toAnswerDto(answerService.createAnswer(answer));
    }

    @PutMapping("/{id}")
    public AnswerDto updateAnswer(@PathVariable UUID id,
                             @Valid @RequestBody AnswerDto answerDto) {
        Answer answer=answerMapper.fromAnswerDto(answerDto);
        return answerMapper.toAnswerDto(answerService.updateAnswer(id,answer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }
}
