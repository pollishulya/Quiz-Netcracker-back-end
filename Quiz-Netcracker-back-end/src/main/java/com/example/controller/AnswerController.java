package com.example.controller;

import com.example.model.Answer;
import com.example.model.Level;
import com.example.service.AnswerService;
import com.example.wrapper.CollectionWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/answer")
@CrossOrigin(origins = {"http://localhost:4200"})
public class AnswerController {
    public final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/{id}")
    public Answer getAnswer(@PathVariable UUID id) {
        return answerService.getAnswerById(id);
    }

    @GetMapping("/all")
    public List<Answer> getAnswer() {
        return answerService.getALL();
    }

    @PostMapping("/answer")
    public Answer createAnswer(@Valid @RequestBody Answer answer) {
        return answerService.createAnswer(answer);
    }

    @PutMapping("/{id}")
    public Answer updateAnswer(@PathVariable UUID id,
                             @Valid @RequestBody Answer answer) {
        return answerService.updateAnswer(id,answer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLevel(@PathVariable UUID id) {
        answerService.deleteAnswer(id);
        return ResponseEntity.ok().build();
    }
}
