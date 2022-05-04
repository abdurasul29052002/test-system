package com.example.testsystem.model;

import com.example.testsystem.entity.Answer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionModel {
    private Long id;

    private String text;

    private List<AnswerModel> answerModelList;
}
