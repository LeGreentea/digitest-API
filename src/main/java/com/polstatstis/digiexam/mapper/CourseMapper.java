package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.CourseDTO;
import com.polstatstis.digiexam.entity.Course;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CourseMapper {

    private final UserService userService;

    public CourseDTO toDTO(Course course) {
        if (course == null) {
            return null;
        }

        Set<Long> teacherIds = course.getTeachers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        Set<Long> studentIds = course.getStudents().stream()
                .map(User::getId)
                .collect(Collectors.toSet());

        Set<Long> examIds = course.getExams().stream()
                .map(exam -> exam.getId())
                .collect(Collectors.toSet());

        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .teacherIds(teacherIds)
                .studentIds(studentIds)
                .examIds(examIds)
                .build();
    }

    public Course toEntity(CourseDTO dto) {
        if (dto == null) {
            return null;
        }

        Set<User> teachers = dto.getTeacherIds().stream()
                .map(userService::findById)
                .collect(Collectors.toSet());

        Set<User> students = dto.getStudentIds().stream()
                .map(userService::findById)
                .collect(Collectors.toSet());

        return Course.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .teachers(teachers)
                .students(students)
                .exams(new HashSet<>())
                .enrollments(new HashSet<>())
                .build();
    }

    public List<CourseDTO> toDTOList(List<Course> courses) {
        return courses.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}