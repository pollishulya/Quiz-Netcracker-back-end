package com.example.service;

import com.example.model.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> findAllQuestion();
    Question saveQuestion(Question question);
    Question updateQuestion(Long questionId, Question questionRequest);
    void deleteQuestion(Long questionId);

    Optional<Question> getQuestionById(Long questionId);
}
