package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.SubmissionDTO;
import com.polstatstis.digiexam.services.SubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmissionById(@PathVariable Long id) {
        SubmissionDTO submissionDTO = submissionService.getSubmissionById(id);
        return ResponseEntity.ok(submissionDTO);
    }

    @GetMapping
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions() {
        List<SubmissionDTO> submissionDTOs = submissionService.getAllSubmissions();
        return ResponseEntity.ok(submissionDTOs);
    }

    @PostMapping
    public ResponseEntity<SubmissionDTO> createSubmission(@RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO createdSubmission = submissionService.createSubmission(submissionDTO);
        return ResponseEntity.ok(createdSubmission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> updateSubmission(@PathVariable Long id, @RequestBody SubmissionDTO submissionDTO) {
        SubmissionDTO updatedSubmission = submissionService.updateSubmission(id, submissionDTO);
        return ResponseEntity.ok(updatedSubmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubmission(@PathVariable Long id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.ok("Submission deleted successfully");
    }
}
