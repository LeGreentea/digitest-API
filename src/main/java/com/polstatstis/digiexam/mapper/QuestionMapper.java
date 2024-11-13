package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.QuestionDTO;
import com.polstatstis.digiexam.entity.Question;

public class QuestionMapper {

    public static QuestionDTO toDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .options(question.getOptions())
                .answer(question.getAnswer())
                .createdById(question.getCreatedBy().getId())
                .build();
    }

    public static Question toEntity(QuestionDTO questionDTO) {
        return Question.builder()
                .id(questionDTO.getId())
                .text(questionDTO.getText())
                .options(questionDTO.getOptions())
                .answer(questionDTO.getAnswer())
                .build();
    }
}
