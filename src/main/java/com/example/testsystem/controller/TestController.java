package com.example.testsystem.controller;

import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.GradeModel;
import com.example.testsystem.model.TestModel;
import com.example.testsystem.payload.CheckDto;
import com.example.testsystem.payload.TestDto;
import com.example.testsystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.TimeLimitExceededException;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    TestService testService;

    @PostMapping
    public ApiResponse addTest(@RequestBody TestDto testDto){
        return testService.addTest(testDto);
    }

    @GetMapping("/generate")
    public TestModel generateTest(@RequestParam Long testId) {
        return testService.generateTest(testId);
    }

    @PostMapping("/check")
    public GradeModel checkTest(@RequestBody CheckDto checkDto) throws TimeLimitExceededException {
        return testService.checkTest(checkDto);
    }

}
