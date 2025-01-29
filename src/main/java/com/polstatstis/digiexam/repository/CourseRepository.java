package com.polstatstis.digiexam.repository;

import com.polstatstis.digiexam.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByTeachersId(Long teacherId);
    List<Course> findByStudentsId(Long studentId);
}