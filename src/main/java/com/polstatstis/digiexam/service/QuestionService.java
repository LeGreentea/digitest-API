package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.QuestionDTO;
import com.polstatstis.digiexam.entity.Question;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.entity.Exam;
import com.polstatstis.digiexam.exception.ExamNotFoundException;
import com.polstatstis.digiexam.exception.QuestionNotFoundException;
import com.polstatstis.digiexam.exception.QuestionValidationException;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.QuestionMapper;
import com.polstatstis.digiexam.repository.ExamRepository;
import com.polstatstis.digiexam.repository.QuestionRepository;
import com.polstatstis.digiexam.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final ExamRepository examRepository;

    public Question findById(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id " + id));
    }

    @Transactional
    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long userId) {
        validateQuestionDTO(questionDTO);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Question question = QuestionMapper.toEntity(questionDTO);
        question.setCreatedBy(user);

        if (questionDTO.getExamId() != null) {
            Exam exam = examRepository.findById(questionDTO.getExamId())
                    .orElseThrow(() -> new ExamNotFoundException("Exam not found"));
            question.setExam(exam);
        }

        Question savedQuestion = questionRepository.save(question);
        return QuestionMapper.toDTO(savedQuestion);
    }

    private void validateQuestionDTO(QuestionDTO dto) {
        if (dto.getText() == null || dto.getText().trim().isEmpty()) {
            throw new QuestionValidationException("Question text is required");
        }

        if (dto.getQuestionType() == null) {
            throw new QuestionValidationException("Question type is required");
        }

        switch (dto.getQuestionType()) {
            case MULTIPLE_CHOICE:
                validateMultipleChoiceQuestion(dto);
                break;

            case TRUE_FALSE:
                validateTrueFalseQuestion(dto);
                break;

            case SHORT_ANSWER:
                validateShortAnswerQuestion(dto);
                break;

            case ESSAY:
                // Essay questions don't require specific validation
                break;

            default:
                throw new QuestionValidationException("Invalid question type");
        }
    }

    private void validateMultipleChoiceQuestion(QuestionDTO dto) {
        if (dto.getOptions() == null || dto.getOptions().size() < 2) {
            throw new QuestionValidationException(
                    "Multiple choice questions must have at least 2 options");
        }

        if (dto.getAnswer() == null || !dto.getOptions().contains(dto.getAnswer())) {
            throw new QuestionValidationException(
                    "Multiple choice answer must be one of the provided options");
        }
    }

    private void validateTrueFalseQuestion(QuestionDTO dto) {
        if (dto.getAnswer() == null ||
                (!dto.getAnswer().equalsIgnoreCase("true") &&
                        !dto.getAnswer().equalsIgnoreCase("false"))) {
            throw new QuestionValidationException(
                    "True/False questions must have a boolean answer (true/false)");
        }
    }

    private void validateShortAnswerQuestion(QuestionDTO dto) {
        if (dto.getAnswer() == null || dto.getAnswer().trim().isEmpty()) {
            throw new QuestionValidationException(
                    "Short answer questions must have an answer");
        }
    }

    public List<QuestionDTO> getAllQuestions() {
        return questionRepository.findAll().stream()
                .map(QuestionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public QuestionDTO getQuestionById(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found"));
        return QuestionMapper.toDTO(question);
    }

    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            throw new QuestionNotFoundException("Question not found");
        }
        questionRepository.deleteById(questionId);
    }

    // In QuestionService.java
    public QuestionDTO getExamQuestionById(Long examId, Long questionId) {
        // First verify the question belongs to the exam
        Question question = questionRepository.findByIdAndExamId(questionId, examId)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found in exam"));
        return QuestionMapper.toDTO(question);
    }
}