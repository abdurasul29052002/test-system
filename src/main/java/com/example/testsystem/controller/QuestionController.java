package com.example.testsystem.controller;

import com.example.testsystem.entity.Question;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.QuestionModel;
import com.example.testsystem.payload.QuestionDto;
import com.example.testsystem.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public QuestionModel getQuestionById(@PathVariable Long id){
        return questionService.getQuestionById(id);
    }

    @GetMapping
    public List<QuestionModel> getQuestionsBySubjectId(@RequestParam Long subjectId){
        return questionService.getQuestionsBySubjectId(subjectId);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteQuestion(@PathVariable Long id){
        return questionService.deleteQuestion(id);
    }
}
