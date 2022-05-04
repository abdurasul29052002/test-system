package com.example.testsystem.repository;

import com.example.testsystem.entity.Answer;
import com.example.testsystem.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
    List<Subject> findAllByEnabled(boolean enabled);

    Optional<Subject> findByIdAndEnabled(Long id, boolean enabled);
}
