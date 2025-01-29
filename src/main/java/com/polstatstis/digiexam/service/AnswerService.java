package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.AnswerDTO;
import com.polstatstis.digiexam.entity.Answer;
import com.polstatstis.digiexam.mapper.AnswerMapper;
import com.polstatstis.digiexam.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private AnswerMapper answerMapper;

    public List<AnswerDTO> getAllAnswers() {
        return answerRepository.findAll().stream()
                .map(answerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public AnswerDTO getAnswerById(Long id) {
        return answerRepository.findById(id)
                .map(answerMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
    }

    public AnswerDTO createAnswer(AnswerDTO answerDTO) {
        Answer answer = answerMapper.toEntity(answerDTO);
        Answer savedAnswer = answerRepository.save(answer);
        return answerMapper.toDTO(savedAnswer);
    }

    public AnswerDTO updateAnswer(Long id, AnswerDTO answerDTO) {
        Answer existingAnswer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        answerMapper.updateAnswerFromDTO(answerDTO, existingAnswer);
        Answer updatedAnswer = answerRepository.save(existingAnswer);
        return answerMapper.toDTO(updatedAnswer);
    }

    public void deleteAnswer(Long id) {
        Answer answer = answerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Answer not found"));
        answerRepository.delete(answer);
    }
}