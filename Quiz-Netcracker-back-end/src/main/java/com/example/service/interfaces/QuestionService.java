package com.example.service.interfaces;

import com.example.model.Question;
import java.util.*;

public interface QuestionService {
    List<Question> findAllQuestion();
    Question saveQuestion(Question question);
    Question updateQuestion(UUID questionId, Question questionRequest);
    void deleteQuestion(UUID questionId);
    Question getQuestionById(UUID questionId);
    List<Question> getQuestionsByCategoryId(UUID category);
}
