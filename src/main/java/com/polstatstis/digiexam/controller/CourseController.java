package com.polstatstis.digiexam.controller;

import com.polstatstis.digiexam.dto.CourseDTO;
import com.polstatstis.digiexam.dto.ResultDTO;
import com.polstatstis.digiexam.entity.Course;
import com.polstatstis.digiexam.mapper.CourseMapper;
import com.polstatstis.digiexam.service.CourseService;
import com.polstatstis.digiexam.service.ResultService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final CourseMapper courseMapper;
    private final ResultService resultService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        List<Course> courses = courseService.findAll();
        return ResponseEntity.ok(courseMapper.toDTOList(courses));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(@PathVariable Long id) {
        Course course = courseService.findById(id);
        return ResponseEntity.ok(courseMapper.toDTO(course));
    }

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(@Valid @RequestBody CourseDTO courseDTO) {
        Course course = courseMapper.toEntity(courseDTO);
        Course savedCourse = courseService.save(course);
        return new ResponseEntity<>(courseMapper.toDTO(savedCourse), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO courseDTO) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        courseDTO.setId(id);
        Course course = courseMapper.toEntity(courseDTO);
        Course updatedCourse = courseService.save(course);
        return ResponseEntity.ok(courseMapper.toDTO(updatedCourse));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        courseService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByTeacherId(@PathVariable Long teacherId) {
        List<Course> courses = courseService.findCoursesByTeacherId(teacherId);
        return ResponseEntity.ok(courseMapper.toDTOList(courses));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<CourseDTO>> getCoursesByStudentId(@PathVariable Long studentId) {
        List<Course> courses = courseService.findCoursesByStudentId(studentId);
        return ResponseEntity.ok(courseMapper.toDTOList(courses));
    }
}