package com.example.controller;

import com.example.model.Question;
import com.example.service.QuestionService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

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
    public Optional<Question> getQuestions(@PathVariable Long questionId) {
        return questionService.getQuestionById(questionId);
    }

    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionService.updateQuestion(questionId, questionRequest);
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}

