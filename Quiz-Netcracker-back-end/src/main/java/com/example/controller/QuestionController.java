package com.example.controller;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Question;
import com.example.repository.QuestionRepository;
import com.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
public class QuestionController {

    private QuestionService questionService;

    @GetMapping("/questions")
    public Page<Question> getQuestions(Pageable pageable) {
        return questionService.findAll(pageable);
    }

    @PostMapping("/questions")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.save(question);
    }

    @PutMapping("/questions/{questionId}")
    public Question updateQuestion(@PathVariable Long questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionService.updateQuestion(questionId, questionRequest);
    }

    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable Long questionId) {
        questionService.delete(questionId);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
}

