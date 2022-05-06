package com.example.testsystem.component;

import com.example.testsystem.entity.User;
import com.example.testsystem.entity.enums.AuthorityType;
import com.example.testsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class Initializer implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByAuthorityType(AuthorityType.ADMIN)) {
            User user = new User(null,
                    "Abdurasul Abduraimov",
                    "legolas",
                    passwordEncoder.encode("12345"),
                    AuthorityType.ADMIN,
                    new ArrayList<>(),
                    true,
                    true,
                    true,
                    true);
            userRepository.save(user);
        }
    }
}
