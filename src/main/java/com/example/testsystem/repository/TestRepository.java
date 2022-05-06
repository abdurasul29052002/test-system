package com.example.testsystem.repository;

import com.example.testsystem.entity.Answer;
import com.example.testsystem.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test,Long> {
}
