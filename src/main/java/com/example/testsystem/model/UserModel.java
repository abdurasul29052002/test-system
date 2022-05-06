package com.example.testsystem.model;

import com.example.testsystem.entity.enums.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;

    private String fullName;

    private String username;

    private List<GradeModel> gradeModelList;

    private AuthorityType authorityType;
}
