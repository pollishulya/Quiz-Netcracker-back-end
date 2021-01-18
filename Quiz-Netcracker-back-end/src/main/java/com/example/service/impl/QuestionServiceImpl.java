package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Answer;
import com.example.model.Question;
import com.example.repository.QuestionRepository;
import com.example.service.interfaces.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> findAllQuestion() {
        return questionRepository.findAll();
    }

    @Override
    public Question saveQuestion(Question question) {
        Question question1 = questionRepository.save(question);
        Set<Answer> answersSet = question1.getAnswersSet()
                .stream()
                .peek(answer -> answer.setQuestion(question1))
                .collect(Collectors.toSet());
        question1.getAnswersSet().clear();
        question1.getAnswersSet().addAll(answersSet);
        return question1;
    }

    @Override
    public Question updateQuestion(UUID questionId, Question questionRequest) {
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
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public Question getQuestionById(UUID questionId) {
        return questionRepository.getQuestionById(questionId);
    }

    @Override
    public List<Question> getQuestionsByCategoryId(UUID categoryId) {
        return questionRepository.getQuestionByCategoryId(categoryId);
    }

    @Override
    public List<Question> getQuestionsByGameId(UUID id) {
        return questionRepository.getQuestionByGameId(id);
    }

}
