package com.example.service.interfaces;

import com.example.model.Question;
import com.example.repository.QuestionRepository;
import com.example.service.impl.QuestionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class QuestionServiceTest {


    @Autowired
    private QuestionServiceImpl questionService;

    @MockBean
    private QuestionRepository questionRepository;

    @Test
    void saveQuestion() {

    }
}