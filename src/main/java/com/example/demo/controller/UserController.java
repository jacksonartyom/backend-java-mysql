package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.exception.SuccessResponse;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/sign-in")
    public SuccessResponse<UserResponse> login(@RequestBody UserRequest req) {
    	SuccessResponse<UserResponse> res = new SuccessResponse<>("success", service.login(req));
        return res;
    }

    @PostMapping("/sign-up")
    public SuccessResponse<String> create(@RequestBody UserRequest req) {
    	SuccessResponse<String> res = new SuccessResponse<>("success", service.createUser(req));
        return res;
    }

    @GetMapping("/user/user-profile")
    public SuccessResponse<UserResponse> getUserProfile(Authentication authentication){
        String userId = (String) authentication.getPrincipal();
        SuccessResponse<UserResponse> res = new SuccessResponse<>("success", service.getUserProfile(userId));
        return res;
    }
}