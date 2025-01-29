package com.polstatstis.digiexam.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class UserDTO {
    private Long id;
    private String email;
    private String name;
    private String role; // Single role as a string
    private Set<CourseDTO> coursesTaught; // Set of courses the user is teaching
    private Set<EnrollmentDTO> enrollments; // Set of enrollments for the user
    private Set<ResultDTO> results; // Set of results for the user
}