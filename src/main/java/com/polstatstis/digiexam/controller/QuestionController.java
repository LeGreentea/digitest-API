package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.QuestionDTO;
import com.polstatstis.digiexam.service.QuestionService;
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
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @Operation(summary = "Membuat pertanyaan baru")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Question already exists") })

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO, @RequestParam Long userId) {
        QuestionDTO createdQuestion = questionService.createQuestion(questionDTO, userId);
        return ResponseEntity.ok(createdQuestion);
    }

    @Operation(summary = "Mendapatkan semua pertanyaan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "No questions found") })

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "Mendapatkan pertanyaan berdasarkan id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Question not found") })

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        QuestionDTO question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @Operation(summary = "Menghapus pertanyaan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question deleted"),
            @ApiResponse(responseCode = "404", description = "Question not found") })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }
}
