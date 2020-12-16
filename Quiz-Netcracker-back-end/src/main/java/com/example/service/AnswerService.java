package com.example.service;

import com.example.model.Answer;

public interface AnswerService {
    Answer createAnswer(Answer answer);
    void deleteAnswer(Long id);
    Answer updateAnswer(Long id, Answer answer);
    Answer getAnswerById(Long id);

}
