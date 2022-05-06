package com.example.testsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CheckDto {
    private Long testId;

    private List<QuestionAnswerDto> answers;
}
