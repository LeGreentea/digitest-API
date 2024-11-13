package com.polstatstis.digiexam.dto;

import lombok.Data;

import java.util.List;

@Data
public class ExamDTO {
    private Long id;
    private String examName;
    private String startDate;
    private String endDate;
    private List<Long> questionIds; // Daftar ID soal yang terkait dengan ujian
}
