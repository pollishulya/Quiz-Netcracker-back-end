package com.example.service;

import com.example.model.Answer;

import java.util.List;
import java.util.UUID;

public interface AnswerService {
    Answer createAnswer(Answer answer);
    void deleteAnswer(UUID id);
    Answer updateAnswer(UUID id, Answer answer);
    Answer getAnswerById(UUID id);
    List<Answer> getALL();
}
