package com.polstatstis.digiexam.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EnrollmentDTO {
    private Long id;
    private UserDTO student;
    private CourseDTO course;
    private LocalDateTime date;
}