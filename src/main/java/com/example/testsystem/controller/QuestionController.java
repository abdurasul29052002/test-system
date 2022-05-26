package com.example.testsystem.controller;

import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.QuestionModel;
import com.example.testsystem.payload.QuestionDto;
import com.example.testsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping
    public ApiResponse addQuestion(@RequestParam Long subjectId, @RequestBody List<QuestionDto> questionDtoList){
        return questionService.addQuestion(subjectId,questionDtoList);
    }

    @PostMapping("/file")
    public ApiResponse addQuestionFromFile(@RequestParam Long subjectId, MultipartHttpServletRequest multipartHttpServletRequest){
        return questionService.addQuestionFromFile(subjectId,multipartHttpServletRequest);
    }

    @GetMapping("/{id}")
    public QuestionModel getQuestionById(@PathVariable Long id){
        return questionService.getQuestionById(id);
    }

    @GetMapping
    public List<QuestionModel> getQuestionsBySubjectId(@RequestParam Long subjectId){
        return questionService.getQuestionsBySubjectId(subjectId);
    }

    @GetMapping("/example")
    public void getExampleFile(HttpServletResponse httpServletResponse){
        questionService.getExampleFile(httpServletResponse);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteQuestion(@PathVariable Long id){
        return questionService.deleteQuestion(id);
    }
}
