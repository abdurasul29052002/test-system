package com.example.testsystem.controller;

import com.example.testsystem.model.ApiResponse;
import com.example.testsystem.payload.UserDto;
import com.example.testsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/byId")
    public UserDto getUserById(){
        return userService.getUserById();
    }

    @PutMapping("/{id}")
    public ApiResponse updateUser(@PathVariable Long id, @RequestBody UserDto userDto){
        return userService.updateUser(userDto,id);
    }

    @DeleteMapping("/{id}")
    public ApiResponse deleteUser(@PathVariable Long id){
        return userService.deleteUser(id);
    }
}
