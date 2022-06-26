package com.example.testsystem.controller;

import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.payload.LoginDto;
import com.example.testsystem.payload.RegisterDto;
import com.example.testsystem.payload.UserDto;
import com.example.testsystem.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody  RegisterDto registerDto){
        return authService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public ApiResponse loginUser(@RequestBody LoginDto loginDto){
        return authService.loginUser(loginDto);
    }
}
