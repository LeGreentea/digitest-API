package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.ExamResultDTO;
import com.polstatstis.digiexam.entity.ExamResult;

public class ExamResultMapper {

    public static ExamResultDTO toDTO(ExamResult examResult) {
        return ExamResultDTO.builder()
                .id(examResult.getId())
                .userId(examResult.getUser().getId())
                .examId(examResult.getExam().getId())
                .score(examResult.getScore())
                .build();
    }

    public static ExamResult toEntity(ExamResultDTO examResultDTO) {
        return ExamResult.builder()
                .id(examResultDTO.getId())
                .score(examResultDTO.getScore())
                .build();
    }
}
