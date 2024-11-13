package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.ExamResultDTO;
import com.polstatstis.digiexam.service.ExamResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/api/exam-results")
@RequiredArgsConstructor
public class ExamResultController {

    private final ExamResultService examResultService;

    @Operation(summary = "Menampilkan hasil ujian berdasarkan id pengguna")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of exam results",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResultDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "No exam results found",
                    content = @Content) })

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ExamResultDTO>> getResultsByUserId(@PathVariable Long userId) {
        List<ExamResultDTO> results = examResultService.getResultsByUserId(userId);
        return ResponseEntity.ok(results);
    }

    @Operation(summary = "Menyimpan hasil ujian")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exam result found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResultDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Exam result not found",
                    content = @Content) })

    @PostMapping
    public ResponseEntity<Void> saveExamResult(@RequestBody ExamResultDTO examResultDTO) {
        examResultService.saveExamResult(examResultDTO);
        return ResponseEntity.ok().build();
    }
}
