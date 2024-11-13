package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.ExamDTO;
import com.polstatstis.digiexam.models.Exam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExamMapper {

    public ExamDTO toExamDTO(Exam exam) {
        if (exam == null) {
            return null;
        }

        ExamDTO examDTO = new ExamDTO();
        examDTO.setId(exam.getId());
        examDTO.setExamName(exam.getExamName());
        examDTO.setStartDate(exam.getStartDate());
        examDTO.setEndDate(exam.getEndDate());
        examDTO.setQuestionIds(exam.getQuestions().stream().map(q -> q.getId()).toList());
        return examDTO;
    }

    public Exam toExam(ExamDTO examDTO) {
        if (examDTO == null) {
            return null;
        }

        Exam exam = new Exam();
        exam.setId(examDTO.getId());
        exam.setExamName(examDTO.getExamName());
        exam.setStartDate(examDTO.getStartDate());
        exam.setEndDate(examDTO.getEndDate());
        // Assuming questions can be fetched by their IDs
        // You would need to fetch actual question entities in the service
        return exam;
    }
}
