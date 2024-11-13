package com.polstatstis.digiexam.services;

import com.polstatstis.digiexam.models.Question;
import com.polstatstis.digiexam.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question addQuestion(Question question) {
        return questionRepository.save(question);
    }

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public List<Question> getQuestionsByExamId(Long examId) {
        return questionRepository.findByExamId(examId);
    }

    public Question updateQuestion(Long questionId, Question question) {
        question.setId(questionId);
        return questionRepository.save(question);
    }

    public boolean deleteQuestion(Long questionId) {
        if (questionRepository.existsById(questionId)) {
            questionRepository.deleteById(questionId);
            return true;
        }
        return false;
    }
}
