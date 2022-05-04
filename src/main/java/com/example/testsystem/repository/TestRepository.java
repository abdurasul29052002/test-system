package com.example.testsystem.repository;

import com.example.testsystem.entity.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Answer,Long> {
}
