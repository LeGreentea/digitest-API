package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.AnswerDTO;
import com.polstatstis.digiexam.dto.ExamDTO;
import com.polstatstis.digiexam.dto.ExamResultDTO;
import com.polstatstis.digiexam.dto.ExamSubmissionDTO;
import com.polstatstis.digiexam.entity.Exam;
import com.polstatstis.digiexam.entity.ExamResult;
import com.polstatstis.digiexam.entity.Question;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.exception.ExamNotFoundException;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.ExamMapper;
import com.polstatstis.digiexam.repository.ExamRepository;
import com.polstatstis.digiexam.repository.ExamResultRepository;
import com.polstatstis.digiexam.repository.QuestionRepository;
import com.polstatstis.digiexam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamService {

    private static final Logger logger = LoggerFactory.getLogger(ExamService.class);

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ExamResultRepository examResultRepository;
    private final ExamMapper examMapper;

    @Transactional
    public ExamDTO createExam(ExamDTO examDTO, Long userId) {
        logger.info("Creating exam for user ID: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Exam exam = examMapper.toEntity(examDTO);
        exam.setCreatedBy(user);

        examRepository.save(exam);
        logger.info("Exam created with ID: {}", exam.getId());
        return examMapper.toDTO(exam);
    }

    public List<ExamDTO> getAllExams() {
        logger.info("Fetching all exams");
        List<ExamDTO> exams = examRepository.findAll().stream()
                .map(examMapper::toDTO)
                .collect(Collectors.toList());
        logger.info("Fetched {} exams", exams.size());
        return exams;
    }

    public ExamDTO getExamById(Long examId) {
        logger.info("Fetching exam with ID: {}", examId);
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Exam not found"));
        return examMapper.toDTO(exam);
    }

    @Transactional
    public void deleteExam(Long examId) {
        logger.info("Deleting exam with ID: {}", examId);
        if (!examRepository.existsById(examId)) {
            throw new ExamNotFoundException("Exam not found");
        }
        examRepository.deleteById(examId);
        logger.info("Deleted exam with ID: {}", examId);
    }

    @Transactional
    public ExamDTO addQuestionToExam(Long examId, Long questionId) {
        logger.info("Adding question ID: {} to exam ID: {}", questionId, examId);

        // Find the exam
        Exam exam = examRepository.findById(examId)
                .orElseThrow(() -> new ExamNotFoundException("Exam not found"));

        // Find the question
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Add the question using the helper method
        exam.addQuestion(question);

        // Save the updated exam
        exam = examRepository.save(exam);
        logger.info("Question {} successfully added to exam {}", questionId, examId);

        return examMapper.toDTO(exam);
    }

    @Transactional
    public ExamResultDTO submitExam(Long examId, ExamSubmissionDTO submission) {
        logger.info("Submitting exam with ID: {} for user ID: {}", examId, submission.getUserId());
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
        logger.info("Exam submitted with result ID: {} and score: {}", examResult.getId(), score);
        return new ExamResultDTO(examResult.getId(), user.getId(), exam.getId(), score);
    }
}