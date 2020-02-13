package com.oopwebsite.service;

import com.oopwebsite.dto.LoginRequest;
import com.oopwebsite.dto.SignUpRequest;
import com.oopwebsite.dto.UserUpdateRequest;
import com.oopwebsite.entity.User;

public interface UserService {
    String login(LoginRequest loginRequest);
    void registerUser(SignUpRequest signUpRequest);
    User updateUser(UserUpdateRequest request);
}
