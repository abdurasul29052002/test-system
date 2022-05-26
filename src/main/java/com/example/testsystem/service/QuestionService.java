package com.example.testsystem.service;

import com.example.testsystem.entity.Answer;
import com.example.testsystem.entity.Question;
import com.example.testsystem.entity.Subject;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.QuestionModel;
import com.example.testsystem.payload.AnswerDto;
import com.example.testsystem.payload.QuestionDto;
import com.example.testsystem.repository.AnswerRepository;
import com.example.testsystem.repository.QuestionRepository;
import com.example.testsystem.repository.SubjectRepository;
import lombok.SneakyThrows;
import org.apache.commons.math3.ode.events.EventHandler;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.util.*;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    CastService castService;

    public ApiResponse addQuestion(Long subjectId, List<QuestionDto> questionDtoList) {
        Optional<Subject> optionalSubject = subjectRepository.findByIdAndEnabled(subjectId, true);
        Subject subject = optionalSubject.orElseThrow(NullPointerException::new);
        List<Question> questionList = new ArrayList<>();
        for (QuestionDto questionDto : questionDtoList) {
            Question question = new Question(null, questionDto.getText(), questionDto.getBall(), subject, new ArrayList<>());
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

    @SneakyThrows
    public ApiResponse addQuestionFromFile(Long subjectId, MultipartHttpServletRequest multipartHttpServletRequest) {
        Subject subject = subjectRepository.findByIdAndEnabled(subjectId, true).orElseThrow(NullPointerException::new);
        List<Question> questionList = new ArrayList<>();
        Iterator<String> fileNames = multipartHttpServletRequest.getFileNames();
        while (fileNames.hasNext()) {
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(fileNames.next());
            XSSFWorkbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Iterator<Sheet> sheetIterator = workbook.iterator();
            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                Iterator<Row> rowIterator = sheet.iterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    if (row.getRowNum() == 0) {
                        continue;
                    }
                    Question question = new Question();
                    List<Answer> answerList = new ArrayList<>();
                    for (Cell cell : row) {
                        if (cell.getColumnIndex() == 0) {
                            question.setText(cell.getStringCellValue());
                            continue;
                        }
                        if (cell.getColumnIndex() == 1) {
                            question.setBall((int) cell.getNumericCellValue());
                            continue;
                        }
                        if (cell.getColumnIndex() == 2) {
                            answerList.add(new Answer(null, true, cell.getStringCellValue(), question));
                        } else {
                            answerList.add(new Answer(null, false, cell.getStringCellValue(), question));
                        }
                    }
                    question.setAnswers(answerList);
                    question.setSubject(subject);
                    questionList.add(question);
                }
            }
        }
        subject.getQuestions().addAll(questionList);
        subjectRepository.save(subject);
        return new ApiResponse("Successfully added", true, null, null);
    }

    public QuestionModel getQuestionById(Long id) {
        Question question = questionRepository.findById(id).orElseThrow(NullPointerException::new);
        return castService.castToQuestionModel(question,false,false);
    }

    public List<QuestionModel> getQuestionsBySubjectId(Long subjectId) {
        List<Question> questionList = questionRepository.findAllBySubjectId(subjectId);
        List<QuestionModel> questionModelList = new ArrayList<>();
        for (Question question : questionList) {
            questionModelList.add(castService.castToQuestionModel(question,false,false));
        }
        return questionModelList;
    }

    public ApiResponse deleteQuestion(Long id) {
        questionRepository.deleteById(id);
        return new ApiResponse("Deleted", true, null, null);
    }

    @SneakyThrows
    public void getExampleFile(HttpServletResponse httpServletResponse) {
        File file = new File("src/main/resources/static/QuestionBase.xlsx");
        FileInputStream fileInputStream = new FileInputStream(file);
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        FileCopyUtils.copy(fileInputStream,httpServletResponse.getOutputStream());
    }
}
