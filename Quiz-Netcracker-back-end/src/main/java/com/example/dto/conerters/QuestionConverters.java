/*package com.example.dto.conerters;

import com.example.dto.QuestionDto;
import com.example.model.Question;

public class QuestionConverters {
    public static QuestionDto convertFromQuestionToDto(Question question) {
        if (question == null) {
            return null;
        }
        QuestionDto questionDto = new QuestionDto();
        //questionDto.setLevel(question.getLevel());
        questionDto.setCategory(CategoryConverters.convert(question.getCategory()));
        questionDto.setDescription(question.getDescription());
        questionDto.setTitle(question.getTitle());
        questionDto.setId(question.getId());
        return questionDto;
    }

    public static Question convertFromDtoToQuestion(QuestionDto questionDto) {
        if (questionDto == null) {
            return null;
        }
        Question question = new Question();
        //question.setLevel(questionDto.getLevel());
        question.setCategory(CategoryConverters.convert(questionDto.getCategory()));
        question.setDescription(questionDto.getDescription());
        question.setTitle(questionDto.getTitle());
        question.setId(questionDto.getId());
        return question;
    }
}*/
