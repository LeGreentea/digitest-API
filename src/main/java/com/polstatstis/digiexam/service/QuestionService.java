package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.QuestionDTO;
import com.polstatstis.digiexam.entity.Question;
import com.polstatstis.digiexam.entity.User;
import com.polstatstis.digiexam.exception.QuestionNotFoundException;
import com.polstatstis.digiexam.exception.UserNotFoundException;
import com.polstatstis.digiexam.mapper.QuestionMapper;
import com.polstatstis.digiexam.repository.QuestionRepository;
import com.polstatstis.digiexam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public QuestionDTO createQuestion(QuestionDTO questionDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Question question = QuestionMapper.toEntity(questionDTO);
        question.setCreatedBy(user); // Set createdBy sebelum menyimpan

        questionRepository.save(question);
        return QuestionMapper.toDTO(question);
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
}
