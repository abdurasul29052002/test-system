package com.example.testsystem.payload;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class QuestionAnswerDto {
    private Long questionId;

    private Long answerId;
}
