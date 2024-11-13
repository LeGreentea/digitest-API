package com.polstatstis.digiexam.repository;

import com.polstatstis.digiexam.entity.ExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface ExamResultRepository extends JpaRepository<ExamResult, Long> {
    @Operation(summary = "Find exam results by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found exam results",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResult.class)) }),
            @ApiResponse(responseCode = "404", description = "Exam results not found",
                    content = @Content) })

    List<ExamResult> findByUserId(Long userId);

    @Operation(summary = "Find exam results by exam ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found exam results",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ExamResult.class)) }),
            @ApiResponse(responseCode = "404", description = "Exam results not found",
                    content = @Content) })

    List<ExamResult> findByExamId(Long examId);
}
