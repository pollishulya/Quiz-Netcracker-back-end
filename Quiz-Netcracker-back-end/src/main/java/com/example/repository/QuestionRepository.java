package com.example.repository;

import com.example.model.Game;
import com.example.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> getQuestionByCategoryId(UUID id);
    Question getQuestionById(UUID id);
    List<Question> getQuestionByGameId(UUID id);
}
