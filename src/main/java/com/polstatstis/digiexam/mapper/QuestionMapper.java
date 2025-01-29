package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.QuestionDTO;
import com.polstatstis.digiexam.entity.*;

public class QuestionMapper {

    public static QuestionDTO toDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .text(question.getText())
                .questionType(question.getQuestionType())
                .createdById(question.getCreatedBy().getId())
                .examId(question.getExam() != null ? question.getExam().getId() : null)
                .options(question instanceof MultipleChoiceQuestion ?
                        ((MultipleChoiceQuestion) question).getOptions() : null)
                .answer(question.getAnswer())
                .build();
    }

    public static Question toEntity(QuestionDTO dto) {
        if (dto.getQuestionType() == null) {
            throw new IllegalArgumentException("Question type cannot be null");
        }

        switch (dto.getQuestionType()) {
            case MULTIPLE_CHOICE:
                return MultipleChoiceQuestion.builder()
                        .id(dto.getId())
                        .text(dto.getText())
                        .options(dto.getOptions())
                        .answer(dto.getAnswer())
                        .build();

            case TRUE_FALSE:
                return TrueFalseQuestion.builder()
                        .id(dto.getId())
                        .text(dto.getText())
                        .answer(Boolean.parseBoolean(dto.getAnswer()))
                        .build();

            case SHORT_ANSWER:
                return ShortAnswerQuestion.builder()
                        .id(dto.getId())
                        .text(dto.getText())
                        .answer(dto.getAnswer())
                        .build();

            case ESSAY:
                return EssayQuestion.builder()
                        .id(dto.getId())
                        .text(dto.getText())
                        .answer(dto.getAnswer())
                        .build();

            default:
                throw new IllegalArgumentException("Unsupported question type: " + dto.getQuestionType());
        }
    }
}