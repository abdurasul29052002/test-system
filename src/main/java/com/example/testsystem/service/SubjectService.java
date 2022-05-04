package com.example.testsystem.service;

import com.example.testsystem.entity.Subject;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.SubjectModel;
import com.example.testsystem.payload.SubjectDto;
import com.example.testsystem.repository.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {

    @Autowired
    SubjectRepository subjectRepository;

    public ApiResponse addSubject(SubjectDto subjectDto) {
        Subject subject = new Subject(null,subjectDto.getName(),true,new ArrayList<>());
        Subject savedSubject = subjectRepository.save(subject);
        return new ApiResponse("Subject saved",true,null,savedSubject.getId());
    }

    public List<SubjectModel> getAllSubjects() {
        List<Subject> subjects = subjectRepository.findAllByEnabled(true);
        List<SubjectModel> subjectModels = new ArrayList<>();
        for (Subject subject : subjects) {
            subjectModels.add(new SubjectModel(subject.getId(),subject.getName()));
        }
        return subjectModels;
    }

    public SubjectModel getSubjectById(Long id) {
        Optional<Subject> optionalSubject = subjectRepository.findByIdAndEnabled(id, true);
        Subject subject = optionalSubject.orElseThrow(NullPointerException::new);
        return new SubjectModel(subject.getId(),subject.getName());
    }

    public ApiResponse updateSubject(Long id, SubjectDto subjectDto){
        Optional<Subject> optionalSubject = subjectRepository.findByIdAndEnabled(id, true);
        Subject subject = optionalSubject.orElseThrow(NullPointerException::new);
        subject.setName(subjectDto.getName());
        subject.setEnabled(subjectDto.isEnabled());
        subjectRepository.save(subject);
        return new ApiResponse("Subject updated",true,null,null);
    }

    public ApiResponse deleteSubject(Long id){
        try {
            subjectRepository.deleteById(id);
            return new ApiResponse("Subject deleted", true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new ApiResponse("Error",false,null,null);
        }
    }
}
