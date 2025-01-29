package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.*;
import com.polstatstis.digiexam.entity.*;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole() != null ? user.getRole().name() : null)
                .coursesTaught(convertCourses(user.getCoursesTaught()))
                .enrollments(convertEnrollments(user.getEnrollments()))
                .results(convertResults(user.getResults()))
                .build();
    }

    private static Set<CourseDTO> convertCourses(Set<Course> courses) {
        if (courses == null) {
            return Set.of(); // Return empty set instead of null
        }
        return courses.stream()
                .map(UserMapper::toCourseDTO)
                .collect(Collectors.toSet());
    }

    private static Set<EnrollmentDTO> convertEnrollments(Set<Enrollment> enrollments) {
        if (enrollments == null) {
            return Set.of(); // Return empty set instead of null
        }
        return enrollments.stream()
                .map(UserMapper::toEnrollmentDTO)
                .collect(Collectors.toSet());
    }

    private static Set<ResultDTO> convertResults(Set<Result> results) {
        if (results == null) {
            return Set.of(); // Return empty set instead of null
        }
        return results.stream()
                .map(UserMapper::toResultDTO)
                .collect(Collectors.toSet());
    }

    private static CourseDTO toCourseDTO(Course course) {
        if (course == null) {
            return null;
        }
        return CourseDTO.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .build();
    }

    private static EnrollmentDTO toEnrollmentDTO(Enrollment enrollment) {
        if (enrollment == null) {
            return null;
        }
        return EnrollmentDTO.builder()
                .id(enrollment.getId())
                .course(toCourseDTO(enrollment.getCourse()))
                .build();
    }

    private static ResultDTO toResultDTO(Result result) {
        if (result == null) {
            return null;
        }
        return ResultDTO.builder()
                .id(result.getId())
                .course(toCourseDTO(result.getCourse()))
                .score(result.getScore())
                .date(result.getDate())
                .build();
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return User.builder()
                .id(userDTO.getId())
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .role(userDTO.getRole() != null ? User.Role.valueOf(userDTO.getRole()) : null)
                .coursesTaught(userDTO.getCoursesTaught() != null ?
                        userDTO.getCoursesTaught().stream()
                                .map(UserMapper::toCourseEntity)
                                .collect(Collectors.toSet()) :
                        Set.of())
                .enrollments(userDTO.getEnrollments() != null ?
                        userDTO.getEnrollments().stream()
                                .map(UserMapper::toEnrollmentEntity)
                                .collect(Collectors.toSet()) :
                        Set.of())
                .results(userDTO.getResults() != null ?
                        userDTO.getResults().stream()
                                .map(UserMapper::toResultEntity)
                                .collect(Collectors.toSet()) :
                        Set.of())
                .build();
    }

    private static Course toCourseEntity(CourseDTO courseDTO) {
        if (courseDTO == null) {
            return null;
        }
        return Course.builder()
                .id(courseDTO.getId())
                .name(courseDTO.getName())
                .description(courseDTO.getDescription())
                .build();
    }

    private static Enrollment toEnrollmentEntity(EnrollmentDTO enrollmentDTO) {
        if (enrollmentDTO == null) {
            return null;
        }
        return Enrollment.builder()
                .id(enrollmentDTO.getId())
                .course(toCourseEntity(enrollmentDTO.getCourse()))
                .build();
    }

    private static Result toResultEntity(ResultDTO resultDTO) {
        if (resultDTO == null) {
            return null;
        }
        return Result.builder()
                .id(resultDTO.getId())
                .course(toCourseEntity(resultDTO.getCourse()))
                .score(resultDTO.getScore())
                .date(resultDTO.getDate())
                .build();
    }
}