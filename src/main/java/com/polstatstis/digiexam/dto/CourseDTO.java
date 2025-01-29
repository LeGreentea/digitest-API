package com.polstatstis.digiexam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;

    @NotBlank(message = "Course name is required")
    @Size(min = 3, max = 255, message = "Course name must be between 3 and 255 characters")
    private String name;

    @NotBlank(message = "Course description is required")
    private String description;

    @Builder.Default
    private Set<Long> teacherIds = new HashSet<>();

    @Builder.Default
    private Set<Long> studentIds = new HashSet<>();

    @Builder.Default
    private Set<Long> examIds = new HashSet<>();
}