package com.example.MyBlogApi.controllers;

import com.example.MyBlogApi.dto.Request.RegisterUserRequest;
import com.example.MyBlogApi.dto.Response.LoginUserResponse;
import com.example.MyBlogApi.entity.User;
import com.example.MyBlogApi.services.concretes.AuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationService;

    @PostMapping("/register")
    public User registerUser(@RequestBody RegisterUserRequest body){
        return authenticationService.registerUser(body.getUsername(), body.getPassword(), body.getEmail(), body.getAge());
    }

    @PostMapping("/login")
    public LoginUserResponse loginUser(@RequestBody RegisterUserRequest body){
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }

}   



