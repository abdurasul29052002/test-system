package com.example.testsystem.service;

import com.example.testsystem.entity.User;
import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.model.GradeModel;
import com.example.testsystem.model.UserModel;
import com.example.testsystem.payload.UserDto;
import com.example.testsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    CastService castService;

    public List<UserModel> getAllUsers() {
        List<User> users = userRepository.findAllByEnabled(true);
        List<UserModel> userModels = new ArrayList<>();
        for (User user : users) {
            UserModel userModel = castService.castToUserModel(user);
            userModels.add(userModel);
        }
        return userModels;
    }

    public UserModel getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findByIdAndEnabled(id, true);
        User user = optionalUser.orElseThrow(NullPointerException::new);
        return castService.castToUserModel(user);
    }

    public ApiResponse updateUser(UserDto userDto,Long id){
        Optional<User> optionalUser = userRepository.findByIdAndEnabled(id, true);
        User user = optionalUser.orElseThrow(NullPointerException::new);
        user.setFullName(userDto.getFullName());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setAuthorityType(userDto.getAuthorityType());
        userRepository.save(user);
        return new ApiResponse("User updated",false,null,null);
    }

    public ApiResponse deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findByIdAndEnabled(id, true);
        User user = optionalUser.orElseThrow(NullPointerException::new);
        user.setEnabled(false);
        userRepository.save(user);
        return new ApiResponse("User deleted",true,null,null);
    }
}
