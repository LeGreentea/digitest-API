package com.polstatstis.digiexam.service;

import com.polstatstis.digiexam.dto.ExamResultDTO;
import com.polstatstis.digiexam.entity.ExamResult;
import com.polstatstis.digiexam.mapper.ExamResultMapper;
import com.polstatstis.digiexam.repository.ExamResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExamResultService {

    private final ExamResultRepository examResultRepository;

    public List<ExamResultDTO> getResultsByUserId(Long userId) {
        return examResultRepository.findByUserId(userId).stream()
                .map(ExamResultMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void saveExamResult(ExamResultDTO examResultDTO) {
        ExamResult examResult = ExamResultMapper.toEntity(examResultDTO);
        examResultRepository.save(examResult);
    }
}
