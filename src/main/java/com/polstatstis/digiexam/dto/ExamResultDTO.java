package com.polstatstis.digiexam.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResultDTO {

    private Long id;
    private Long userId;
    private Long examId;
    private Integer score;
}
