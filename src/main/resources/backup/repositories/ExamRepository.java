package com.polstatstis.digiexam.repositories;

import com.polstatstis.digiexam.models.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    List<Exam> findByStartDateBeforeAndEndDateAfter(LocalDateTime startDate, LocalDateTime endDate);
    List<Exam> findByStartDateAfter(LocalDateTime startDate);
    List<Exam> findByEndDateBefore(LocalDateTime endDate);
}
