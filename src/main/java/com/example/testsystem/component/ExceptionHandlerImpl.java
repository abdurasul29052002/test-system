package com.example.testsystem.component;

import com.example.testsystem.exception.AlreadyEndException;
import com.example.testsystem.exception.NotStartException;
import com.example.testsystem.exception.TestCompletedException;
import com.example.testsystem.model.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerImpl {

    @ExceptionHandler({TestCompletedException.class})
    public HttpEntity<?> handleException(TestCompletedException e){
        return ResponseEntity.status(409).body(new ApiResponse("This test is already completed",false,null,null));
    }

    @ExceptionHandler({NotStartException.class})
    public HttpEntity<?> handleException(NotStartException e){
        return ResponseEntity.status(409).body(new ApiResponse("This test is not started",false,null,null));
    }

    @ExceptionHandler({AlreadyEndException.class})
    public HttpEntity<?> handleException(AlreadyEndException e){
        return ResponseEntity.status(409).body(new ApiResponse("This test is already ended",false,null,null));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
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
