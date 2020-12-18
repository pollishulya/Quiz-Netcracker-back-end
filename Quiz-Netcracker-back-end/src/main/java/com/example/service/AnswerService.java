package com.example.service;

import com.example.model.Answer;

import java.util.UUID;

public interface AnswerService {
    Answer createAnswer(Answer answer);
    void deleteAnswer(UUID id);
    Answer updateAnswer(UUID id, Answer answer);
    Answer getAnswerById(UUID id);

}
