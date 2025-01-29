// ExamDTO.java
package com.polstatstis.digiexam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDTO {

    private Long id;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters")
    private String title;

    private String description;

    @NotNull(message = "Date is required")
    private LocalDateTime date;

    @NotNull(message = "Duration is required")
    @Min(value = 30, message = "Duration must be at least 30 minutes")
    @Max(value = 300, message = "Duration cannot exceed 300 minutes")
    private Integer duration;

    @Builder.Default
    private List<Long> questionIds = new ArrayList<>();

    @NotNull(message = "Created by user ID is required")
    private Long createdById;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Helper methods for exam status
    public boolean isOngoing() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(date) && now.isBefore(getEndTime());
    }

    public boolean isUpcoming() {
        return LocalDateTime.now().isBefore(date);
    }

    public boolean isCompleted() {
        return LocalDateTime.now().isAfter(getEndTime());
    }

    public LocalDateTime getEndTime() {
        return date.plusMinutes(duration);
    }

    public String getStatus() {
        if (isUpcoming()) return "upcoming";
        if (isOngoing()) return "ongoing";
        return "completed";
    }
}