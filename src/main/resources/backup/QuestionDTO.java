package com.polstatstis.digiexam.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private Long id;
    private String questionText;
    private List<String> options;
    private String correctAnswer;
    private String questionType; // Multiple Choice, True/False, etc.
}
