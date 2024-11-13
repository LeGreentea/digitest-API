package com.polstatstis.digiexam.services;

import com.polstatstis.digiexam.models.Exam;
import com.polstatstis.digiexam.repositories.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ExamService {

    @Autowired
    private ExamRepository examRepository;

    public Exam createExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Optional<Exam> getExamById(Long examId) {
        return examRepository.findById(examId);
    }

    public List<Exam> getUpcomingExams() {
        return examRepository.findByStartDateAfter(LocalDateTime.now());
    }

    public List<Exam> getPastExams() {
        return examRepository.findByEndDateBefore(LocalDateTime.now());
    }

    public Exam updateExam(Long examId, Exam exam) {
        exam.setId(examId);
        return examRepository.save(exam);
    }

    public boolean deleteExam(Long examId) {
        if (examRepository.existsById(examId)) {
            examRepository.deleteById(examId);
            return true;
        }
        return false;
    }
}
