package com.example.testsystem.service;

import com.example.testsystem.entity.*;
import com.example.testsystem.exception.AlreadyEndException;
import com.example.testsystem.exception.NotStartException;
import com.example.testsystem.exception.TestCompletedException;
import com.example.testsystem.model.*;
import com.example.testsystem.payload.CheckDto;
import com.example.testsystem.payload.QuestionAnswerDto;
import com.example.testsystem.payload.TestDto;
import com.example.testsystem.repository.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    GeneratedTestRepository generatedTestRepository;
    @Autowired
    CastService castService;
    @Autowired
    GradeRepository gradeRepository;

    public ApiResponse addTest(TestDto testDto) {
        Test test = new Test(null,
                testDto.getName(),
                testDto.getQuestionsCount(),
                testDto.getStartsAt(),
                testDto.getEndsAt(),
                subjectRepository.findByIdAndEnabled(testDto.getSubjectId(), true).orElseThrow(NullPointerException::new));
        Test savedTest = testRepository.save(test);
        return new ApiResponse("Test saved", true, null, savedTest.getId());
    }

    @SneakyThrows
    public GeneratedTestModel generateTest(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(NullPointerException::new);
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!(currentTime.after(test.getStartsAt())&&currentTime.before(test.getEndsAt()))){
            throw new NotStartException();
        }
        List<Question> questionList = questionRepository.findAllBySubjectId(test.getSubject().getId());
        List<Question> generatedQuestionList = new ArrayList<>();
        for (int i = 0; i < test.getQuestionsCount(); i++) {
            int randomNumber = new Random().nextInt(questionList.size());
            generatedQuestionList.add(questionList.get(randomNumber));
            questionList.remove(randomNumber);
        }
        try {
            GeneratedTest savedGeneratedTest = generatedTestRepository.save(new GeneratedTest(null,
                    principal,
                    test, false,
                    generatedQuestionList));
            return castService.castToGeneratedTestModel(savedGeneratedTest);
        }catch (DataIntegrityViolationException e){
            GeneratedTest generatedTest = generatedTestRepository.findByUserIdAndTestId(principal.getId(), testId).orElseThrow(NullPointerException::new);
            return castService.castToGeneratedTestModel(generatedTest);
        }
    }

    @SneakyThrows
    public List<QuestionModel> checkTest(CheckDto checkDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GeneratedTest generatedTest = generatedTestRepository.findByUserIdAndTestId(user.getId(), checkDto.getTestId()).orElseThrow(NullPointerException::new);
        if (generatedTest.isCompleted()){
            throw new TestCompletedException();
        }
        Test test = testRepository.findById(checkDto.getTestId()).orElseThrow(NullPointerException::new);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!(currentTime.after(test.getStartsAt())&&currentTime.before(test.getEndsAt()))){
            throw new AlreadyEndException();
        }
        List<Question> questionList = generatedTest.getQuestionList();
        List<QuestionAnswerDto> answers = checkDto.getAnswers();
        int gradeSum=0;
        List<QuestionModel> questionModelList = new ArrayList<>();
        for (int i = 0; i < questionList.size(); i++) {
            QuestionAnswerDto answer = answers.get(i);
            for (Question question : questionList) {
                if (question.getId()==answer.getQuestionId()){
                    if (question.getCorrectAnswer().getId()==answer.getAnswerId()){
                        gradeSum+=question.getBall();
                        questionModelList.add(castService.castToQuestionModel(question,true,true));
                    }else {
                        questionModelList.add(castService.castToQuestionModel(question,false,true));
                    }
                }
            }
        }
        Grade grade = new Grade(null,user,generatedTest,gradeSum);
        generatedTest.setCompleted(true);
        gradeRepository.save(grade);

        return questionModelList;
    }

    public List<TestModel> getTestsByUserId(){
        User authentication = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Test> allByCreatedBy = testRepository.findAllByCreatedBy(authentication.getId());
        List<TestModel> testModelList = new ArrayList<>();
        for (Test test : allByCreatedBy) {
            testModelList.add(castService.castToTestModel(test));
        }
        return testModelList;
    }

    public GeneratedTestModel generateTest(TestDto testDto) {
        Subject subject = subjectRepository.findById(testDto.getSubjectId()).orElseThrow(NullPointerException::new);
        Test test = new Test(null,testDto.getName(),testDto.getQuestionsCount(),testDto.getStartsAt(),testDto.getEndsAt(),subject);
        Test savedTest = testRepository.save(test);
        return generateTest(savedTest.getId());
    }

    private int checkQuestion(List<Question> questionList, QuestionAnswerDto answer,int gradeSum){
        for (Question question : questionList) {
            if (question.getId()==answer.getQuestionId()){
                if (question.getCorrectAnswer().getId()==answer.getAnswerId()){
                    gradeSum+=question.getBall();
                    return gradeSum;
                }
            }
        }
        return gradeSum;
    }
}
