package com.example.testsystem.service;

import com.example.testsystem.entity.*;
import com.example.testsystem.entity.enums.AuthorityType;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.GradeModel;
import com.example.testsystem.model.TestModel;
import com.example.testsystem.payload.CheckDto;
import com.example.testsystem.payload.QuestionAnswerDto;
import com.example.testsystem.payload.TestDto;
import com.example.testsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.TimeLimitExceededException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.Period;
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

    public TestModel generateTest(Long testId) {
        Test test = testRepository.findById(testId).orElseThrow(NullPointerException::new);
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!checkUser(principal)) {
            throw new RuntimeException();
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
            return castService.castToTestModel(savedGeneratedTest);
        }catch (DataIntegrityViolationException e){
            GeneratedTest generatedTest = generatedTestRepository.findByUserIdAndTestId(principal.getId(), testId).orElseThrow(NullPointerException::new);
            return castService.castToTestModel(generatedTest);
        }
    }

    public GradeModel checkTest(CheckDto checkDto) throws TimeLimitExceededException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        GeneratedTest generatedTest = generatedTestRepository.findByUserIdAndTestId(user.getId(), checkDto.getTestId()).orElseThrow(NullPointerException::new);
        Test test = testRepository.findById(checkDto.getTestId()).orElseThrow(NullPointerException::new);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        if (!(currentTime.after(test.getStartsAt())&&currentTime.before(test.getEndsAt()))){
            throw new TimeLimitExceededException();
        }
        List<Question> questionList = generatedTest.getQuestionList();
        List<QuestionAnswerDto> answers = checkDto.getAnswers();
        int gradeSum=0;
        for (int i = 0; i < questionList.size(); i++) {
            gradeSum = checkQuestion(questionList,answers.get(i),gradeSum);
        }
        Grade grade = new Grade(null,user,generatedTest,gradeSum);
        Grade save = gradeRepository.save(grade);
        return castService.castToGradeModel(save);
    }

    private boolean checkUser(UserDetails user) {
        for (GrantedAuthority authority : user.getAuthorities()) {
            return authority.getAuthority().equals(AuthorityType.STUDENT.name());
        }
        return false;
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
