package com.example.testsystem.service;

import com.example.testsystem.entity.*;
import com.example.testsystem.model.*;
import com.example.testsystem.payload.UserDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CastService {
    public AnswerModel castToAnswerModel(Answer answer,boolean sendCorrectAnswer){
        AnswerModel answerModel = new AnswerModel(answer.getId(), answer.getText(), false);
        if (sendCorrectAnswer){
            answerModel.setCorrect(answer.isCorrect());
        }
        return answerModel;
    }

    public QuestionModel castToQuestionModel(Question question, boolean correct,boolean sendCorrectAnswer){
        List<AnswerModel> answerModelList = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            answerModelList.add(castToAnswerModel(answer,sendCorrectAnswer));
        }
        return  new QuestionModel(question.getId(),question.getText(),question.getBall(),correct,answerModelList);
    }

    public GeneratedTestModel castToGeneratedTestModel(GeneratedTest generatedTest){
        List<QuestionModel> questionModelList = new ArrayList<>();
        for (Question question : generatedTest.getQuestionList()) {
            questionModelList.add(castToQuestionModel(question,false,false));
        }
        return new GeneratedTestModel(generatedTest.getId(),generatedTest.getTest().getId(),questionModelList);
    }

    public UserDto castToUserDto(User user){
        List<GradeModel> gradeModelList = new ArrayList<>();
        for (Grade grade : user.getGradeList()) {
            gradeModelList.add(castToGradeModel(grade));
        }
        return new UserDto(user.getId(), user.getFullName(), user.getUsername(), gradeModelList,user.getAuthorityType());
    }

    public GradeModel castToGradeModel(Grade grade){
        return new GradeModel(grade.getId(),grade.getUser().getId(),grade.getGeneratedTest().getId(),grade.getGrade());
    }

    public TestModel castToTestModel(Test test){
        return new TestModel(
                test.getId(),
                test.getName(),
                test.getQuestionsCount(),
                test.getStartsAt(),
                test.getEndsAt(),
                test.getSubject().getId()
        );
    }
}
