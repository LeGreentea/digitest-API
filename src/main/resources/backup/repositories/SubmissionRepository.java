package com.polstatstis.digiexam.repositories;

import com.polstatstis.digiexam.models.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {

    List<Submission> findByUserId(Long userId);
    List<Submission> findByExamId(Long examId);
}
