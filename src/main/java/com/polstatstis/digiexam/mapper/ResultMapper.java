package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.ResultDTO;
import com.polstatstis.digiexam.entity.Result;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.entity.Exam;
import com.polstatstis.digiexam.entity.Course;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ResultMapper {
    private final ExamMapper examMapper;
    private final UserMapper userMapper;
    private final CourseMapper courseMapper;

    public ResultDTO toDTO(Result result) {
        if (result == null) {
            return null;
        }

        return ResultDTO.builder()
                .id(result.getId())
                .exam(examMapper.toDTO(result.getExam()))
                .student(userMapper.toDTO(result.getStudent()))
                .course(courseMapper.toDTO(result.getCourse()))
                .score(result.getScore())
                .date(result.getDate())
                .build();
    }

    public Result toEntity(ResultDTO resultDTO, User student, Exam exam, Course course) {
        if (resultDTO == null) {
            return null;
        }

        return Result.builder()
                .id(resultDTO.getId())
                .exam(exam)
                .student(student)
                .course(course)
                .score(resultDTO.getScore())
                .date(resultDTO.getDate())
                .build();
    }
}