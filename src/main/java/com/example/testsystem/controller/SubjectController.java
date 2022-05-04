package com.example.testsystem.controller;

import com.example.testsystem.entity.Subject;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.SubjectModel;
import com.example.testsystem.payload.SubjectDto;
import com.example.testsystem.service.SubjectService;
import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {

    @Autowired
    SubjectService subjectService;

    @PostMapping
    public ApiResponse addSubject(@RequestBody @Valid SubjectDto subjectDto){
        return subjectService.addSubject(subjectDto);
    }

    @GetMapping
    public List<SubjectModel> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public SubjectModel getSubjectById(@PathVariable Long id){
        return subjectService.getSubjectById(id);
    }

    @PutMapping("/{id}")
    public ApiResponse updateSubject(@PathVariable Long id,@RequestBody SubjectDto subjectDto){
        return subjectService.updateSubject(id,subjectDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteSubject(@PathVariable Long id){
        return subjectService.deleteSubject(id);
    }
}
