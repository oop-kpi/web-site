package com.oopwebsite.controller;

import com.oopwebsite.controller.exceptions.UserAlreadyExistsException;
import com.oopwebsite.dto.LoginRequest;
import com.oopwebsite.dto.SignUpRequest;
import com.oopwebsite.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api("Authentication service")
@RequestMapping("/api/auth")
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    @ApiOperation(value = "User login", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully authenticated, JWT token is returned"),
            @ApiResponse(code = 403, message = "Invalid credentials")})
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping(value = "/signup", consumes={"application/json"})
    @ApiOperation(value = "User signup request", response = Void.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User successfully signed up"),
            @ApiResponse(code = 400, message = "User already exists")})
    public ResponseEntity<?> registerUser( @RequestBody SignUpRequest signUpRequest) {
        userService.registerUser(signUpRequest);
        return ResponseEntity.ok().body( "User successfully signed up");
    }

}
