package com.example.service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    public Page<Question> findAll(org.springframework.data.domain.Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Question updateQuestion(Long questionId, Question questionRequest) {
        return questionRepository.findById(questionId).map(question -> {
            question.setTitle(questionRequest.getTitle());
            question.setDescription(questionRequest.getDescription());
            question.setCategory(questionRequest.getCategory());
            question.setLevel(questionRequest.getLevel());
            return questionRepository.save(question);
        }).orElseThrow(() -> new ResourceNotFoundException("Question not found with id " + questionId));
    }

    public void delete(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

}
