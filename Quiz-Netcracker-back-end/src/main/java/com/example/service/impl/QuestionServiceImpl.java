package com.example.service.impl;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Question;
import com.example.repository.QuestionRepository;
import com.example.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(UUID questionId, Question questionRequest) {
        return questionRepository.findById(questionId).map(question -> {
            question.setTitle(questionRequest.getTitle());
            question.setDescription(questionRequest.getDescription());
            question.setCategory(questionRequest.getCategory());
            question.setLevel(questionRequest.getLevel());
            question.setAnswersSet(questionRequest.getAnswersSet());
            return questionRepository.save(question);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    @Override
    public void deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
    }

    @Override
    public Optional<Question> getQuestionById(UUID questionId){
        return questionRepository.findById(questionId);
    }

    @Override
    public List<Question> getQuestionsByCategoryId(UUID categoryId) {
        return questionRepository.getQuestionByCategoryId(categoryId);
    }
}
