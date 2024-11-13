package com.polstatstis.digiexam.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime date;
    private List<Long> questionIds;
    private Long createdById;
}
