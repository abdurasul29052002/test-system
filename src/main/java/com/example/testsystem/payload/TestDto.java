package com.example.testsystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestDto {
    private String name;

    private Long subjectId;

    private Integer questionsCount;

    private Timestamp startsAt;

    private Timestamp endsAt;
}
