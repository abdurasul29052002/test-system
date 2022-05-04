package com.example.testsystem.service;

import com.example.testsystem.entity.Answer;
import com.example.testsystem.entity.Question;
import com.example.testsystem.entity.Subject;
import com.example.testsystem.model.AnswerModel;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.QuestionModel;
import com.example.testsystem.payload.AnswerDto;
import com.example.testsystem.payload.QuestionDto;
import com.example.testsystem.repository.AnswerRepository;
import com.example.testsystem.repository.QuestionRepository;
import com.example.testsystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    SubjectRepository subjectRepository;

    public ApiResponse addQuestion(Long subjectId, List<QuestionDto> questionDtoList) {
        Optional<Subject> optionalSubject = subjectRepository.findByIdAndEnabled(subjectId, true);
        Subject subject = optionalSubject.orElseThrow(NullPointerException::new);
        List<Question> questionList = new ArrayList<>();
        for (QuestionDto questionDto : questionDtoList) {
            Question question = new Question(null, questionDto.getText(), subject, new ArrayList<>());
            List<Answer> answerList = new ArrayList<>();
            for (AnswerDto answerDto : questionDto.getAnswerDtoList()) {
                Answer answer = new Answer(null, answerDto.isCorrect(), answerDto.getText(), question);
                answerList.add(answer);
            }
            question.setAnswers(answerList);
            questionList.add(question);
        }
        subject.getQuestions().addAll(questionList);
        subjectRepository.save(subject);
        return new ApiResponse("Questions are saved", true, null, null);
    }

//    public List<QuestionModel> getAllQuestion(){
//        List<Question> questionList = questionRepository.findAll();
//        List<QuestionModel> questionModelList = new ArrayList<>();
//        for (Question question : questionList) {
//            List<AnswerModel> answerModelList = new ArrayList<>();
//            for (Answer answer : question.getAnswers()) {
//                answerModelList.add(new AnswerModel(answer.getId(),answer.getText()));
//            }
//            questionModelList.add(new QuestionModel(question.getId(),question.getText(),answerModelList));
//        }
//        return  questionModelList;
//    }TODO: Hamma Question lar ni olish yo`lini muhokama qilish kerak

    public QuestionModel getQuestionById(Long id){
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        List<AnswerModel> answerModelList = new ArrayList<>();
        for (Answer answer : question.getAnswers()) {
            answerModelList.add(new AnswerModel(answer.getId(),answer.getText()));
        }
        return new QuestionModel(question.getId(),question.getText(),answerModelList);
    }

    public List<QuestionModel> getQuestionsBySubjectId(Long subjectId){
        List<Question> questionList = questionRepository.findAllBySubjectId(subjectId);
        List<QuestionModel> questionModelList = new ArrayList<>();
        for (Question question : questionList) {
            List<AnswerModel> answerModelList = new ArrayList<>();
            for (Answer answer : question.getAnswers()) {
                answerModelList.add(new AnswerModel(answer.getId(),answer.getText()));
            }
            questionModelList.add(new QuestionModel(question.getId(),question.getText(),answerModelList));
        }
        return  questionModelList;
    }

//    public ApiResponse updateQuestion(Long id,QuestionDto questionDto){
//
//    } TODO: Question update ni muhokama qilish kerak

    public ApiResponse deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return new ApiResponse("Deleted", true, null, null);
    }
}
