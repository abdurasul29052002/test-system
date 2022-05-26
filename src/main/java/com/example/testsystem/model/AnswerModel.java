package com.example.testsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerModel {
    private Long id;

    private String text;

    private boolean correct;
}
