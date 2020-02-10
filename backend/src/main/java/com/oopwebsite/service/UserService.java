package com.oopwebsite.service;

import com.oopwebsite.dto.LoginRequest;
import com.oopwebsite.dto.SignUpRequest;

public interface UserService {
    String login(LoginRequest loginRequest);
    void registerUser(SignUpRequest signUpRequest);
}
