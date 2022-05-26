package com.example.testsystem.controller;

import com.example.testsystem.model.*;
import com.example.testsystem.payload.CheckDto;
import com.example.testsystem.payload.TestDto;
import com.example.testsystem.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.naming.TimeLimitExceededException;
import java.util.List;

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
    public GeneratedTestModel generateTest(@RequestParam Long testId) {
        return testService.generateTest(testId);
    }

    @PostMapping("/generate")
    public GeneratedTestModel generateTest(@RequestBody TestDto testDto){
        return testService.generateTest(testDto);
    }

    @PostMapping("/check")
    public List<QuestionModel> checkTest(@RequestBody CheckDto checkDto) throws TimeLimitExceededException {
        return testService.checkTest(checkDto);
    }

    @GetMapping("/byUser")
    public List<TestModel> getTestsByUserId(){
        return testService.getTestsByUserId();
    }

}
