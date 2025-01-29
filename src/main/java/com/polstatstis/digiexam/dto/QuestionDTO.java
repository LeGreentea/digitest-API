package com.polstatstis.digiexam.dto;

import com.polstatstis.digiexam.entity.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class QuestionDTO {
    private Long id;

    @NotBlank(message = "Question text is required")
    private String text;

    @NotNull(message = "Question type is required")
    private QuestionType questionType;

    private Long createdById;
    private Long examId;

    // Multiple Choice specific fields
    @Size(min = 2, message = "Multiple choice questions must have at least 2 options")
    private List<String> options;

    // Answer field (used by all question types)
    private String answer;
}