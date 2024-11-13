package com.polstatstis.digiexam.dto;

import lombok.Data;

@Data
public class SubmissionDTO {
    private Long id;
    private Long userId; // ID pengguna yang mengirimkan ujian
    private Long examId; // ID ujian yang disubmit
    private String submissionDate;
    private String status; // Misalnya: "submitted", "pending", "graded"
    private int score; // Nilai ujian
}
