package com.example.testsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestModel {
    private Long id;

    private String name;

    private Integer questionsCount;

    private Timestamp startsAt;

    private Timestamp endsAt;

    private Long subjectId;
}
