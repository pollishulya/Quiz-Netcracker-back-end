package com.example.service.impl;

import com.example.model.Answer;
import com.example.repository.AnswerRepository;
import com.example.service.AnswerService;
import org.springframework.stereotype.Service;

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
    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }

    @Override
    public Answer updateAnswer(Long id, Answer answerReq)
    {
        return answerRepository.findById(id).map(answer->{
            answer.setTitle(answerReq.getTitle());
            answer.setRight(answerReq.isRight());
            return  answerRepository.save(answer);
        }).orElseThrow(()-> new NullPointerException("Object not found"));
    }

    @Override
    public Answer getAnswerById(Long id) {
        return answerRepository.findAnswerById(id);
    }
}
