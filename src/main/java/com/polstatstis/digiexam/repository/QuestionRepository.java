package com.polstatstis.digiexam.repository;

import com.polstatstis.digiexam.entity.Question;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Operation(summary = "Find questions by user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found questions",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Question.class))}),
            @ApiResponse(responseCode = "404", description = "Questions not found",
                    content = @Content)})

    List<Question> findByCreatedById(Long userId);
}
