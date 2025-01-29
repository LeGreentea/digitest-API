package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.ExamDTO;
import com.polstatstis.digiexam.dto.ExamResultDTO;
import com.polstatstis.digiexam.dto.ExamSubmissionDTO;
import com.polstatstis.digiexam.service.ExamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @Operation(summary = "buat ujian baru oleh dosen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exam created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })

    // preauthorize only admin and lecturer can create exam
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @PostMapping
    public ResponseEntity<ExamDTO> createExam(@RequestBody ExamDTO examDTO, @RequestParam Long userId) {
        ExamDTO createdExam = examService.createExam(examDTO, userId);
        return ResponseEntity.ok(createdExam);
    }

    @Operation(summary = "Update exam with new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam updated successfully"),
            @ApiResponse(responseCode = "404", description = "Exam not found"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @PutMapping("/{id}/questions")
    public ResponseEntity<ExamDTO> addQuestionToExam(
            @PathVariable Long id,
            @RequestParam Long questionId) {
        ExamDTO updatedExam = examService.addQuestionToExam(id, questionId);
        return ResponseEntity.ok(updatedExam);
    }


    @Operation(summary = "mendapatkan semua ujian")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exams retrieved successfully"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })

    // preauthorize only admin and lecturer can get all exams
    @PreAuthorize("hasRole('STUDENT') or hasRole('LECTURER')")
    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams() {
        List<ExamDTO> exams = examService.getAllExams();
        return ResponseEntity.ok(exams);
    }

    @Operation(summary = "mendapatkan ujian berdasarkan id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exam retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Exam not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(@PathVariable Long id) {
        ExamDTO exam = examService.getExamById(id);
        return ResponseEntity.ok(exam);
    }

    @Operation(summary = "menghapus ujian oleh dosen")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Exam updated successfully"),
        @ApiResponse(responseCode = "404", description = "Exam not found"),
        @ApiResponse(responseCode = "500", description = "Server error")
    })

    // preauthorize only admin and lecturer can delete exam
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(@PathVariable Long id) {
        examService.deleteExam(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Submit ujian oleh mahasiswa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam submitted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResultDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Exam or User not found",
                    content = @Content)
    })

    @PreAuthorize("hasRole('STUDENT')")
    @PostMapping("/{id}/submit")
    public ResponseEntity<ExamResultDTO> submitExam(@PathVariable Long id, @RequestBody ExamSubmissionDTO submission) {
        ExamResultDTO result = examService.submitExam(id, submission);
        return ResponseEntity.ok(result);
    }
}
