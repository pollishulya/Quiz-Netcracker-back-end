package com.example.service.impl;

import com.example.model.Category;
import com.example.model.Game;
import com.example.model.Level;
import com.example.model.Question;
import com.example.repository.QuestionRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertTrue;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuestionServiceImplTest {

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    void saveQuestion() {
//        Question question = new Question();
//        questionService.saveQuestion(question);
//
//        Mockito.verify(questionRepository).save(question);
    }

    @Test
    void getQuestionById() {
//        Question question = new Question();
//        questionService.saveQuestion(question);
//
//        Question questionById = questionService.getQuestionById(question.getId());
//
//        Assert.assertEquals(question,questionById);
//
//        questionService.deleteQuestion(question.getId());
    }
}