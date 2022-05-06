package com.example.testsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDto {
    private String text;

    private Integer ball;

    /* Answer's field */
    List<AnswerDto> answerDtoList;
}
