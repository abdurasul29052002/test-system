package com.example.testsystem.repository;

import com.example.testsystem.entity.Answer;
import com.example.testsystem.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    List<Question> findAllBySubjectId(Long subject_id);
}
