package com.polstatstis.digiexam.services;

import com.polstatstis.digiexam.models.Submission;
import com.polstatstis.digiexam.repositories.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public Submission submitExam(Submission submission) {
        return submissionRepository.save(submission);
    }

    public Optional<Submission> getSubmissionById(Long submissionId) {
        return submissionRepository.findById(submissionId);
    }

    public List<Submission> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId);
    }

    public List<Submission> getSubmissionsByExam(Long examId) {
        return submissionRepository.findByExamId(examId);
    }

    public boolean deleteSubmission(Long submissionId) {
        if (submissionRepository.existsById(submissionId)) {
            submissionRepository.deleteById(submissionId);
            return true;
        }
        return false;
    }
}
