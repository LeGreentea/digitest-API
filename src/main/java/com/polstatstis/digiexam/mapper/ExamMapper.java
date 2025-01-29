// ExamMapper.java
package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.ExamDTO;
import com.polstatstis.digiexam.entity.Course;
import com.polstatstis.digiexam.entity.Exam;
import com.polstatstis.digiexam.entity.Question;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.service.CourseService;
import com.polstatstis.digiexam.service.QuestionService;
import com.polstatstis.digiexam.service.UserService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExamMapper {

    private final QuestionService questionService;
    private final UserService userService;
    private final CourseService courseService;

    public ExamMapper(QuestionService questionService,
                      UserService userService,
                      CourseService courseService) {
        this.questionService = questionService;
        this.userService = userService;
        this.courseService = courseService;
    }

    public ExamDTO toDTO(Exam exam) {
        if (exam == null) {
            return null;
        }

        List<Long> questionIds = exam.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toList());

        return ExamDTO.builder()
                .id(exam.getId())
                .title(exam.getTitle())
                .description(exam.getDescription())
                .date(exam.getDate())
                .duration(exam.getDuration())
                .questionIds(questionIds)
                .createdById(exam.getCreatedBy().getId())
                .courseId(exam.getCourse().getId())
                .createdAt(exam.getCreatedAt())
                .updatedAt(exam.getUpdatedAt())
                .build();
    }

    public Exam toEntity(ExamDTO examDTO) {
        if (examDTO == null) {
            return null;
        }

        List<Question> questions = examDTO.getQuestionIds().stream()
                .map(questionService::findById)
                .collect(Collectors.toList());

        User createdBy = userService.findById(examDTO.getCreatedById());
        Course course = courseService.findById(examDTO.getCourseId());

        Exam exam = Exam.builder()
                .id(examDTO.getId())
                .title(examDTO.getTitle())
                .description(examDTO.getDescription())
                .date(examDTO.getDate())
                .duration(examDTO.getDuration())
                .questions(questions)
                .createdBy(createdBy)
                .course(course)
                .build();

        // Set bi-directional relationships
        questions.forEach(question -> question.setExam(exam));

        return exam;
    }

    public List<ExamDTO> toDTOList(List<Exam> exams) {
        return exams.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}