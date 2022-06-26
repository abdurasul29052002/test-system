package com.example.testsystem.payload;

import com.example.testsystem.entity.enums.AuthorityType;
import com.example.testsystem.model.GradeModel;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDto {
    @NonNull
    private Long id;

    @NonNull
    private String fullName;

    @NonNull
    private String username;

    private String password;

    @NonNull
    private List<GradeModel> gradeModelList;

    @NonNull
    private AuthorityType authorityType;
}
