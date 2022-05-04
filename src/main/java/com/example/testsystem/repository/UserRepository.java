package com.example.testsystem.repository;

import com.example.testsystem.entity.User;
import com.example.testsystem.entity.enums.AuthorityType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsernameAndEnabled(String username, boolean enabled);

    Optional<User> findByUsername(String username);

    List<User> findAllByEnabled(boolean enabled);

    boolean existsByAuthorityType(AuthorityType authorityType);

    Optional<User> findByIdAndEnabled(Long id, boolean enabled);
}
