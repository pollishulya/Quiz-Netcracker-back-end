package com.example.service.interfaces;

import com.example.model.Question;
import java.util.*;

public interface QuestionService {
    List<Question> findAllQuestion();
    Question saveQuestion(Question question);
    Question updateQuestion(UUID id, Question questionRequest);
    void deleteQuestion(UUID id);
    Question getQuestionById(UUID id);
    List<Question> getQuestionsByCategoryId(UUID id);
    List<Question> getQuestionsByGameId(UUID id);
    void setQuestionToAnswers(Question question);
}
