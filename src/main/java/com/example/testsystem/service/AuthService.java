package com.example.testsystem.service;

import com.example.testsystem.component.JwtProvider;
import com.example.testsystem.entity.User;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.payload.LoginDto;
import com.example.testsystem.payload.RegisterDto;
import com.example.testsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtProvider jwtProvider;

    public ApiResponse registerUser(RegisterDto registerDto){
        User user = new User(null,
                registerDto.getFullName(),
                registerDto.getUsername(),
                passwordEncoder.encode(registerDto.getPassword()),
                registerDto.getAuthorityType(),
                new ArrayList<>(),
                true,true,true,true);
        User savedUser = userRepository.save(user);
        return new ApiResponse("User saved",true,null,savedUser.getId());
    }

    public ApiResponse loginUser(LoginDto loginDto) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        ));
        String token = jwtProvider.generateToken(loginDto.getUsername());
        return new ApiResponse("Login successfully",true,token,null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElseThrow(()->new BadCredentialsException("User not found"));
    }
}
