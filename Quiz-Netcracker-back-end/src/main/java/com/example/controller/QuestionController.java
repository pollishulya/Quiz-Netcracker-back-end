package com.example.controller;

import com.example.dto.QuestionDto;
import com.example.model.Question;
import com.example.service.interfaces.QuestionService;
import com.example.service.mapper.QuestionMapper;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @Autowired
    public QuestionController(QuestionService questionService, QuestionMapper questionMapper) {
        this.questionService = questionService;
        this.questionMapper = questionMapper;
    }

    @GetMapping("/findAllQuestions")
    public List<QuestionDto> getQuestions() {
        return questionService.findAllQuestion().stream()
                .map(questionMapper::toShortQuestionDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/findQuestion/{questionId}")
    public QuestionDto getQuestions(@PathVariable UUID questionId) {
        return questionMapper.toShortQuestionDto(questionService.getQuestionById(questionId));
    }

    @PostMapping("/save")
    public QuestionDto createQuestion(@Valid @RequestBody QuestionDto questionDto) {
        Question question = questionMapper.fromQuestionDto(questionDto);
        return questionMapper.toQuestionDto(questionService.saveQuestion(question));
    }

    @PutMapping("/update/{questionId}")
    public QuestionDto updateQuestion(@PathVariable UUID questionId,
                                      @Valid @RequestBody QuestionDto questionRequest) {
        Question question = questionMapper.fromQuestionDto(questionRequest);
        return questionMapper.toQuestionDto(questionService.updateQuestion(questionId, question));
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}

