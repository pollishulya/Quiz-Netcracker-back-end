package com.example.service;

import com.example.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface QuestionService {
    Page<Question> findAllQuestion(Pageable pageable);
    Question saveQuestion(Question question);
    Question updateQuestion(Long questionId, Question questionRequest);
    void deleteQuestion(Long questionId);

    Optional<Question> getQuestionById(Long questionId);
}
