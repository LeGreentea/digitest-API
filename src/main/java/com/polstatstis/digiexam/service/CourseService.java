package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.entity.Course;
import com.polstatstis.digiexam.exception.ResourceNotFoundException;
import com.polstatstis.digiexam.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    @Transactional(readOnly = true)
    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Course> findCoursesByTeacherId(Long teacherId) {
        return courseRepository.findByTeachersId(teacherId);
    }

    @Transactional(readOnly = true)
    public List<Course> findCoursesByStudentId(Long studentId) {
        return courseRepository.findByStudentsId(studentId);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return courseRepository.existsById(id);
    }
}