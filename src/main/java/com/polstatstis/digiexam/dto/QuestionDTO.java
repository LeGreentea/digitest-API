package com.polstatstis.digiexam.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionDTO {

    private Long id;
    private String text;
    private List<String> options;
    private String answer;
    private Long createdById;
}
