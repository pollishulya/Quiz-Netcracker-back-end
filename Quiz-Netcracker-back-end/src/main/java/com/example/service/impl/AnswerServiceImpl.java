package com.example.service.impl;

import com.example.exception.DeleteEntityException;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Answer;
import com.example.repository.AnswerRepository;
import com.example.service.interfaces.AnswerService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final MessageSource messageSource;

    public AnswerServiceImpl(AnswerRepository answerRepository, MessageSource messageSource) {
        this.answerRepository = answerRepository;
        this.messageSource = messageSource;
    }

    @Override
    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public void deleteAnswer(UUID id) {
        try {
            answerRepository.deleteById(id);
        }
        catch (RuntimeException exception) {
            UUID[] args = new UUID[]{ id };
            throw new DeleteEntityException(ErrorInfo.DELETE_ENTITY_ERROR,
                    messageSource.getMessage("message.DeleteEntityError", args, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public Answer updateAnswer(UUID id, Answer answerReq)
    {
        UUID[] args = new UUID[]{ id };
        return answerRepository.findById(id).map(answer->{
            answer.setTitle(answerReq.getTitle());
            answer.setRight(answerReq.getRight());
            return  answerRepository.save(answer);
        }).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public Answer getAnswerById(UUID id) {
        UUID[] args = new UUID[]{ id };
        return answerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public List<Answer> getALL() {
        return answerRepository.findAll();
    }
}
