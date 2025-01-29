package com.polstatstis.digiexam.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ResultDTO {
    private Long id;
    private ExamDTO exam;
    private UserDTO student;
    private CourseDTO course;
    private Double score;
    private LocalDateTime date;
}