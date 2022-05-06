package com.example.testsystem.service;

import com.example.testsystem.entity.*;
import com.example.testsystem.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CastService {
    public AnswerModel castToAnswerModel(Answer answer){
        return new AnswerModel(answer.getId(),answer.getText());
    }

    public QuestionModel castToQuestionModel(Question question){
        List<AnswerModel> answerModelList = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            answerModelList.add(castToAnswerModel(answer));
        }
        return  new QuestionModel(question.getId(),question.getText(),question.getBall(),answerModelList);
    }

    public TestModel castToTestModel(GeneratedTest generatedTest){
        List<QuestionModel> questionModelList = new ArrayList<>();
        for (Question question : generatedTest.getQuestionList()) {
            questionModelList.add(castToQuestionModel(question));
        }
        return new TestModel(generatedTest.getId(),generatedTest.getTest().getId(),questionModelList);
    }

    public UserModel castToUserModel(User user){
        List<GradeModel> gradeModelList = new ArrayList<>();
        for (Grade grade : user.getGradeList()) {
            gradeModelList.add(castToGradeModel(grade));
        }
        return new UserModel(user.getId(), user.getFullName(), user.getUsername(), gradeModelList,user.getAuthorityType());
    }

    public GradeModel castToGradeModel(Grade grade){
        return new GradeModel(grade.getId(),grade.getUser().getId(),grade.getGeneratedTest().getId(),grade.getGrade());
    }
}
