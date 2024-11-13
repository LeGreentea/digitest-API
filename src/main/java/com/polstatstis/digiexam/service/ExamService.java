package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.ExamDTO;
import com.polstatstis.digiexam.dto.ExamResultDTO;
import com.polstatstis.digiexam.dto.ExamSubmissionDTO;
import com.polstatstis.digiexam.entity.Exam;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.ExamMapper;
import com.polstatstis.digiexam.repository.ExamRepository;
import com.polstatstis.digiexam.exception.ExamNotFoundException;
import com.polstatstis.digiexam.repository.UserRepository;
import com.polstatstis.digiexam.repository.ExamResultRepository;
import com.polstatstis.digiexam.repository.QuestionRepository;
import com.polstatstis.digiexam.entity.ExamResult;
import com.polstatstis.digiexam.entity.Question;
import com.polstatstis.digiexam.dto.AnswerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ExamResultRepository examResultRepository;

        public ExamDTO createExam(ExamDTO examDTO, Long userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("User not found"));

            Exam exam = ExamMapper.toEntity(examDTO);
            exam.setCreatedBy(user); // Set createdBy sebelum menyimpan

            examRepository.save(exam);
            return ExamMapper.toDTO(exam);
        }

    public List<ExamDTO> getAllExams() {
        return examRepository.findAll().stream()
                .map(ExamMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ExamDTO getExamById(Long examId) {
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Exam not found"));
        return ExamMapper.toDTO(exam);
    }

    public void deleteExam(Long examId) {
        if (!examRepository.existsById(examId)) {
            throw new ExamNotFoundException("Exam not found");
        }
        examRepository.deleteById(examId);
    }

    public ExamResultDTO submitExam(Long examId, ExamSubmissionDTO submission) {
        User user = userRepository.findById(submission.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Exam not found"));

        Map<Long, String> correctAnswers = questionRepository.findAllById(
                submission.getAnswers().stream()
                        .map(AnswerDTO::getQuestionId)
                        .collect(Collectors.toList())
        ).stream().collect(Collectors.toMap(Question::getId, Question::getAnswer));

        int score = 0;
        for (AnswerDTO answer : submission.getAnswers()) {
            if (correctAnswers.get(answer.getQuestionId()).equals(answer.getAnswer())) {
                score++;
            }
        }

        ExamResult examResult = ExamResult.builder()
                .user(user)
                .exam(exam)
                .score(score)
                .build();

        examResultRepository.save(examResult);
        return new ExamResultDTO(examResult.getId(), user.getId(), exam.getId(), score);
    }
}
