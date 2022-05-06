package com.example.testsystem.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeModel {
    private  Long id;

    private Long userId;

    private Long generatedTestId;

    private  Integer grade;
}
