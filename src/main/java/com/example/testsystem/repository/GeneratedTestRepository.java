package com.example.testsystem.repository;

import com.example.testsystem.entity.GeneratedTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneratedTestRepository extends JpaRepository<GeneratedTest,Long> {
    Optional<GeneratedTest> findByUserIdAndTestId(Long user_id, Long test_id);
}
