package com.example.service;

import com.example.model.Question;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionService {
    List<Question> findAllQuestion();
    Question saveQuestion(Question question);
    Question updateQuestion(UUID questionId, Question questionRequest);
    void deleteQuestion(UUID questionId);

    Optional<Question> getQuestionById(UUID questionId);
    List<Question> getQuestionsByCategoryId(UUID category);
}
