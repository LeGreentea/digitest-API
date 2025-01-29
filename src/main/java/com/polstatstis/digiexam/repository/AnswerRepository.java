package com.polstatstis.digiexam.repository;

import com.polstatstis.digiexam.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByExamId(Long examId);
    List<Answer> findByStudentId(Long studentId);
}