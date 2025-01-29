package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.ResultDTO;
import com.polstatstis.digiexam.entity.Result;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.entity.Exam;
import com.polstatstis.digiexam.entity.Course;
import com.polstatstis.digiexam.mapper.ResultMapper;
import com.polstatstis.digiexam.repository.ResultRepository;
import com.polstatstis.digiexam.repository.UserRepository;
import com.polstatstis.digiexam.repository.ExamRepository;
import com.polstatstis.digiexam.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResultService {

    private final ResultRepository resultRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final ResultMapper resultMapper;

    public List<ResultDTO> getResultsByStudentId(Long studentId) {
        List<Result> results = resultRepository.findByStudentId(studentId);
        return results.stream()
                .map(resultMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResultDTO saveResult(ResultDTO resultDTO) {
        try {
            log.info("Finding student with ID: {}", resultDTO.getStudent().getId());
            User student = userRepository.findById(resultDTO.getStudent().getId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            log.info("Finding exam with ID: {}", resultDTO.getExam().getId());
            Exam exam = examRepository.findById(resultDTO.getExam().getId())
                    .orElseThrow(() -> new RuntimeException("Exam not found"));

            log.info("Finding course with ID: {}", resultDTO.getCourse().getId());
            Course course = courseRepository.findById(resultDTO.getCourse().getId())
                    .orElseThrow(() -> new RuntimeException("Course not found"));

            Result result = Result.builder()
                    .exam(exam)
                    .student(student)
                    .course(course)
                    .score(resultDTO.getScore())
                    .date(LocalDateTime.now())
                    .build();

            log.info("Saving result: {}", result);
            Result savedResult = resultRepository.save(result);
            log.info("Result saved successfully with ID: {}", savedResult.getId());

            return resultMapper.toDTO(savedResult);
        } catch (Exception e) {
            log.error("Error in saveResult: {}", e.getMessage(), e);
            throw e;
        }
    }

    public ResultDTO getResult(Long id) {
        log.info("Fetching result with id: {}", id);
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + id));
        return resultMapper.toDTO(result);
    }

    @Transactional
    public ResultDTO updateResult(ResultDTO resultDTO) {
        log.info("Updating result with id: {}", resultDTO.getId());

        Result existingResult = resultRepository.findById(resultDTO.getId())
                .orElseThrow(() -> new RuntimeException("Result not found"));

        User student = userRepository.findById(resultDTO.getStudent().getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Exam exam = examRepository.findById(resultDTO.getExam().getId())
                .orElseThrow(() -> new RuntimeException("Exam not found"));

        Course course = courseRepository.findById(resultDTO.getCourse().getId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        // Update the existing result
        existingResult.setExam(exam);
        existingResult.setStudent(student);
        existingResult.setCourse(course);
        existingResult.setScore(resultDTO.getScore());

        Result updatedResult = resultRepository.save(existingResult);
        return resultMapper.toDTO(updatedResult);
    }

    @Transactional
    public void deleteResult(Long id) {
        log.info("Deleting result with id: {}", id);
        Result result = resultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found"));
        resultRepository.delete(result);
    }

    @Transactional(readOnly = true)
    public List<ResultDTO> getResultsByCourseId(Long courseId) {
        log.info("Finding results for course with ID: {}", courseId);
        List<Result> results = resultRepository.findByCourseId(courseId);
        return results.stream()
                .map(resultMapper::toDTO)
                .collect(Collectors.toList());
    }
}
