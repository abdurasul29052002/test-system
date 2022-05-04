package com.example.testsystem.payload;

import com.example.testsystem.entity.enums.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String fullName;

    private String username;

    private String password;

    private AuthorityType authorityType;
}
