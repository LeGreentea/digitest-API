package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.ResultDTO;
import com.polstatstis.digiexam.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/{id}")
    @Operation(summary = "Get result by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result found",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Result not found")
    })
    public ResponseEntity<ResultDTO> getResult(@PathVariable Long id) {
        log.info("Fetching result with id: {}", id);
        try {
            ResultDTO result = resultService.getResult(id);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            log.error("Error fetching result: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all results for a student")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Results found",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class)))
    })
    public ResponseEntity<List<ResultDTO>> getResultsByStudent(@PathVariable Long studentId) {
        log.info("Fetching results for student with id: {}", studentId);
        List<ResultDTO> results = resultService.getResultsByStudentId(studentId);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    @Operation(summary = "Save new result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result saved successfully",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResultDTO> saveResult(@RequestBody ResultDTO resultDTO) {
        log.info("Received result submission: {}", resultDTO);
        try {
            ResultDTO savedResult = resultService.saveResult(resultDTO);
            log.info("Successfully saved result: {}", savedResult);
            return ResponseEntity.ok(savedResult);
        } catch (Exception e) {
            log.error("Error saving result: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Update result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result updated successfully",
                    content = @Content(schema = @Schema(implementation = ResultDTO.class))),
            @ApiResponse(responseCode = "404", description = "Result not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ResultDTO> updateResult(@PathVariable Long id, @RequestBody ResultDTO resultDTO) {
        log.info("Updating result with id: {} with data: {}", id, resultDTO);
        try {
            resultDTO.setId(id);
            ResultDTO updatedResult = resultService.updateResult(resultDTO);
            log.info("Successfully updated result: {}", updatedResult);
            return ResponseEntity.ok(updatedResult);
        } catch (RuntimeException e) {
            log.error("Error updating result: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error updating result: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete result")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Result deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Result not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        log.info("Deleting result with id: {}", id);
        try {
            resultService.deleteResult(id);
            log.info("Successfully deleted result with id: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Error deleting result: {}", e.getMessage(), e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting result: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/course/{courseId}")
    @PreAuthorize("hasAnyRole('LECTURER', 'ADMIN')")
    public ResponseEntity<List<ResultDTO>> getResultsByCourse(@PathVariable Long courseId) {
        log.info("Fetching results for course with id: {}", courseId);
        try {
            List<ResultDTO> results = resultService.getResultsByCourseId(courseId);
            log.info("Found {} results for course {}", results.size(), courseId);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            log.error("Error fetching results for course {}: {}", courseId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}