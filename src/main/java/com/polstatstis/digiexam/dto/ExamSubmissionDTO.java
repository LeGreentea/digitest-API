package com.polstatstis.digiexam.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamSubmissionDTO {
    private Long userId;
    private Long examId;
    private List<AnswerDTO> answers;
}
