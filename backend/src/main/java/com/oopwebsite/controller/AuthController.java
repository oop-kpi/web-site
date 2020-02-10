package com.oopwebsite.controller;

import com.oopwebsite.controller.exceptions.UserAlreadyExistsException;
import com.oopwebsite.dto.LoginRequest;
import com.oopwebsite.dto.SignUpRequest;
import com.oopwebsite.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/signup", consumes={"application/json"})
    public ResponseEntity<?> registerUser( @RequestBody SignUpRequest signUpRequest) {
        userService.registerUser(signUpRequest);
        return ResponseEntity.ok().body( "User registered successfully");
    }

}
