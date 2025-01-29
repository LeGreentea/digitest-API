package com.polstatstis.digiexam.mapper;

import com.polstatstis.digiexam.dto.AnswerDTO;
import com.polstatstis.digiexam.entity.Answer;
import com.polstatstis.digiexam.entity.MultipleChoiceQuestion;
import com.polstatstis.digiexam.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

@Mapper(componentModel = "spring")
public abstract class AnswerMapper {

    @Autowired
    private ApplicationContext applicationContext;

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "student.id", target = "studentId")
    @Mapping(source = "exam.id", target = "examId")
    public abstract AnswerDTO toDTO(Answer answer);

    @Mapping(source = "questionId", target = "question", qualifiedByName = "questionFactory")
    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "examId", target = "exam.id")
    public abstract Answer toEntity(AnswerDTO answerDTO);

    @Mapping(source = "questionId", target = "question", qualifiedByName = "questionFactory")
    @Mapping(source = "studentId", target = "student.id")
    @Mapping(source = "examId", target = "exam.id")
    public abstract void updateAnswerFromDTO(AnswerDTO answerDTO, @MappingTarget Answer answer);

    @Named("questionFactory")
    protected Question mapQuestion(Long questionId) {
        // Replace with your logic to get the appropriate Question implementation
        // For example, using a QuestionService to fetch the question by ID
        // QuestionService questionService = applicationContext.getBean(QuestionService.class);
        // return questionService.findById(questionId);

        // Placeholder logic, replace with actual implementation
        Question question = new MultipleChoiceQuestion();
        question.setId(questionId);
        return question;
    }
}