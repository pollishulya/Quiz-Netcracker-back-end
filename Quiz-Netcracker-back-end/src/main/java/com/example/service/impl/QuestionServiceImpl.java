package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.detail.ErrorInfo;
import com.example.model.Answer;
import com.example.model.Question;
import com.example.repository.QuestionRepository;
import com.example.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final MessageSource messageSource;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository, MessageSource messageSource) {
        this.questionRepository = questionRepository;
        this.messageSource = messageSource;
    }

    @Override
    public List<Question> findAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Question saveQuestion(Question question) {
        Question question1 = questionRepository.save(question);
        setQuestionToAnswers(question1);
        return question1;
    }

    @Override
    public Question updateQuestion(UUID questionId, Question questionRequest) {
        UUID[] args = new UUID[]{ questionId };
        return questionRepository.findById(questionId).map(question -> {
            question.setTitle(questionRequest.getTitle());
            question.setDescription(questionRequest.getDescription());
            question.setCategory(questionRequest.getCategory());
            question.setLevel(questionRequest.getLevel());
            if (questionRequest.getAnswersSet() != null) {
                question.getAnswersSet().clear();
                question.getAnswersSet().addAll(questionRequest.getAnswersSet());
            }
            return questionRepository.save(question);
        }).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale())));
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public Question getQuestionById(UUID questionId) {
        UUID[] args = new UUID[]{ questionId };
        return questionRepository.findById(questionId).orElseThrow(()-> new ResourceNotFoundException(ErrorInfo.RESOURCE_NOT_FOUND,
                messageSource.getMessage("message.ResourceNotFound", args, LocaleContextHolder.getLocale()))) ;
    }

    @Override
    public List<Question> getQuestionsByCategoryId(UUID categoryId) {
        return questionRepository.getQuestionByCategoryId(categoryId);
    }

    @Override
    public List<Question> getQuestionsByGameId(UUID id) {
        return questionRepository.getQuestionByGameId(id);
    }

    public void setQuestionToAnswers(Question question1) {
        Set<Answer> answersSet = question1.getAnswersSet()
                .stream()
                .peek(answer -> answer.setQuestion(question1))
                .collect(Collectors.toSet());
        question1.getAnswersSet().clear();
        question1.getAnswersSet().addAll(answersSet);
    }
}
