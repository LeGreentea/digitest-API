package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.QuestionDTO;
import com.polstatstis.digiexam.exception.QuestionValidationException;
import com.polstatstis.digiexam.service.QuestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

//    @Operation(summary = "Create a new question")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Question created",
//                    content = { @Content(mediaType = "application/json",
//                            schema = @Schema(implementation = QuestionDTO.class)) }),
//            @ApiResponse(responseCode = "400", description = "Invalid input"),
//            @ApiResponse(responseCode = "409", description = "Question already exists") })
//    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
//    @PostMapping
//    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO, @RequestParam Long userId) {
//        QuestionDTO createdQuestion = questionService.createQuestion(questionDTO, userId);
//        return ResponseEntity.ok(createdQuestion);
//    }

    @Operation(summary = "Create a new question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question created"),
            @ApiResponse(responseCode = "400", description = "Invalid question data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(
            @Valid @RequestBody QuestionDTO questionDTO,
            @RequestParam Long userId) {
        return ResponseEntity.ok(questionService.createQuestion(questionDTO, userId));
    }

    @ExceptionHandler(QuestionValidationException.class)
    public ResponseEntity<String> handleQuestionValidationException(QuestionValidationException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @Operation(summary = "Get all questions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of questions",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "No questions found") })
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<QuestionDTO> questions = questionService.getAllQuestions();
        return ResponseEntity.ok(questions);
    }

    @Operation(summary = "Get a question by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Question found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = QuestionDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Question not found") })
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        QuestionDTO question = questionService.getQuestionById(id);
        return ResponseEntity.ok(question);
    }

    @PreAuthorize("hasRole('STUDENT')")
    @GetMapping("/exam/{examId}/questions/{questionId}")
    public ResponseEntity<QuestionDTO> getExamQuestionById(
            @PathVariable Long examId,
            @PathVariable Long questionId) {
        // First verify if the student has access to this exam
        // Then return the question
        QuestionDTO question = questionService.getExamQuestionById(examId, questionId);
        return ResponseEntity.ok(question);
    }

    @Operation(summary = "Delete a question")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Question deleted"),
            @ApiResponse(responseCode = "404", description = "Question not found") })
    @PreAuthorize("hasRole('ADMIN') or hasRole('LECTURER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
        return ResponseEntity.noContent().build();
    }


}