package com.polstatstis.digiexam.repository;

import com.polstatstis.digiexam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    @Operation(summary = "Find exams created by a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found exams created by the user",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Exam.class))}),
            @ApiResponse(responseCode = "404", description = "No exams found",
                    content = @Content)})

    List<Exam> findByCreatedById(Long userId);
}
