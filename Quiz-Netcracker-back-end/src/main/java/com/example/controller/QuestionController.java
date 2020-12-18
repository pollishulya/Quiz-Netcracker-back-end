package com.example.controller;

import com.example.model.Question;
import com.example.service.QuestionService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/questions")
    public CollectionWrapper<Question> getQuestions() {
        return new CollectionWrapper<>(questionService.findAllQuestion());
    }

    @GetMapping("/questions/{questionId}")
    public Optional<Question> getQuestions(@PathVariable UUID questionId) {
        return questionService.getQuestionById(questionId);
    }

    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable UUID questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionService.updateQuestion(questionId, questionRequest);
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}

