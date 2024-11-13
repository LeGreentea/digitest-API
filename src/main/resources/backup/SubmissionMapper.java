package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.SubmissionDTO;
import com.polstatstis.digiexam.models.Submission;
import org.springframework.stereotype.Component;

@Component
public class SubmissionMapper {

    public SubmissionDTO toSubmissionDTO(Submission submission) {
        if (submission == null) {
            return null;
        }

        SubmissionDTO submissionDTO = new SubmissionDTO();
        submissionDTO.setId(submission.getId());
        submissionDTO.setUserId(submission.getUser().getId());
        submissionDTO.setExamId(submission.getExam().getId());
        submissionDTO.setSubmissionDate(submission.getSubmissionDate());
        submissionDTO.setStatus(submission.getStatus().name());
        submissionDTO.setScore(submission.getScore());
        return submissionDTO;
    }

    public Submission toSubmission(SubmissionDTO submissionDTO) {
        if (submissionDTO == null) {
            return null;
        }

        Submission submission = new Submission();
        submission.setId(submissionDTO.getId());
        // User and Exam entities will be fetched based on IDs
        // This logic should be handled by the service layer
        return submission;
    }
}
