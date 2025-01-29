package com.polstatstis.digiexam.repository;

import com.polstatstis.digiexam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCreatedById(Long userId);

    // Add these methods
    Optional<Question> findByIdAndExamId(Long id, Long examId);

    // Alternative method using @Query for more complex queries if needed
    @Query("SELECT q FROM Question q WHERE q.id = :questionId AND q.exam.id = :examId")
    Optional<Question> findQuestionInExam(@Param("questionId") Long questionId, @Param("examId") Long examId);
}