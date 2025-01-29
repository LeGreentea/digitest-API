package com.polstatstis.digiexam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerDTO {
    private Long id;
    private String answerText;
    private Long questionId;
    private Long studentId;
    private Long examId;
    private Double score;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getAnswer() {
        return answerText;
    }
}