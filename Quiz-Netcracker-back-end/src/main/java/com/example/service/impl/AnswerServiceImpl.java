package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Answer;
import com.example.repository.AnswerRepository;
import com.example.service.AnswerService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    public AnswerServiceImpl(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    @Override
    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(UUID id) {
        answerRepository.deleteById(id);
    }

    @Override
    public Answer updateAnswer(UUID id, Answer answerReq)
    {
        return answerRepository.findById(id).map(answer->{
            answer.setTitle(answerReq.getTitle());
            answer.setRight(answerReq.isRight());
            return  answerRepository.save(answer);
        }).orElseThrow(()-> new ResourceNotFoundException("Object not found"));
    }

    @Override
    public Answer getAnswerById(UUID id) {
        return answerRepository.findAnswerById(id);
    }

    @Override
    public List<Answer> getALL() {
        return answerRepository.findAll();
    }
}
