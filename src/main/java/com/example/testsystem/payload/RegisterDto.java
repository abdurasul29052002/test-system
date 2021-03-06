package com.example.testsystem.payload;

import com.example.testsystem.entity.enums.AuthorityType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@ConditionalOnWebApplication
@AllArgsConstructor
public class RegisterDto {
    @NotNull
    private String fullName;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private AuthorityType authorityType;
}
