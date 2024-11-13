package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.ExamDTO;
import com.polstatstis.digiexam.entity.Exam;

public class ExamMapper {

    public static ExamDTO toDTO(Exam exam) {
        return ExamDTO.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .date(exam.getDate())
                .questionIds(exam.getQuestions())
                .createdById(exam.getCreatedBy().getId())
                .build();
    }

    public static Exam toEntity(ExamDTO examDTO) {
        return Exam.builder()
                .id(examDTO.getId())
                .title(examDTO.getTitle())
                .description(examDTO.getDescription())
                .date(examDTO.getDate())
                .questions(examDTO.getQuestionIds())
                .build();
    }
}
