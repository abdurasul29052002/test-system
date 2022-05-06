package com.example.testsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {
    private Long id;

    private Long testId;

    private List<QuestionModel> questionModelList;
}
