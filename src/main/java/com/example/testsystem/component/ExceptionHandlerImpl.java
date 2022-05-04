package com.example.testsystem.component;

import com.example.testsystem.model.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerImpl {
    @ExceptionHandler({RuntimeException.class,MethodArgumentNotValidException.class})
    public HttpEntity<?> handleException(Exception e) {
        return ResponseEntity.status(400).body(new ApiResponse("Something went terribly wrong please check the form",false,null,null));
    }

    @ExceptionHandler({BadCredentialsException.class})
    public HttpEntity<?> handleException(BadCredentialsException e) {
        return ResponseEntity.status(400).body(new ApiResponse("Username or Password error",false,null,null));
    }

    @ExceptionHandler({NullPointerException.class})
    public HttpEntity<?> handleException(NullPointerException e){
        return ResponseEntity.status(404).body(new ApiResponse("Not found",false,null,null));
    }
}