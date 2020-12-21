package com.example.controller;

import com.example.model.Question;
import com.example.service.QuestionService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("/findAllQuestions")
    public CollectionWrapper<Question> getQuestions() {
        return new CollectionWrapper<>(questionService.findAllQuestion());
    }

    @GetMapping("/findQuestion/{questionId}")
    public Optional<Question> getQuestions(@PathVariable UUID questionId) {
        return questionService.getQuestionById(questionId);
    }

    @PostMapping("/save")
    public Question createQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @PutMapping("/update/{questionId}")
    public Question updateQuestion(@PathVariable UUID questionId,
                                   @Valid @RequestBody Question questionRequest) {
        return questionService.updateQuestion(questionId, questionRequest);
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok().build();
    }
}

