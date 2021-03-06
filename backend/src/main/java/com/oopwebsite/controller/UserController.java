package com.oopwebsite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.dto.UserUpdateRequest;
import com.oopwebsite.entity.User;
import com.oopwebsite.entity.view.View;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.service.UserService;
import com.oopwebsite.wrappers.UserWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@Api("User information service")
@RequestMapping("api/user")
public class UserController {
    private UserRepository repository;
    private UserService service;

    public UserController(UserRepository repository,UserService service) {
        this.repository = repository;
        this.service = service;
    }

    @ApiOperation(value = "Update user", response = User.class)
    @PreAuthorize("@authComponentImpl.canUpdateUser(#login,#wrapper)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 400,message = "Wrong id")})
    @PatchMapping("{login}/update")
    public ResponseEntity<?> updateUserData(@RequestBody UserUpdateRequest request,
                                         @PathVariable("login") String login,
                                         @AuthenticationPrincipal UserWrapper wrapper){
        request.setPrevLogin(login);
        service.updateUser(request);
        return ResponseEntity.ok().body("Successfully!");
    }
    @GetMapping("me")
    @ApiOperation(value = "Get current user", response = User.class)
    @JsonView(View.FULL_INFORMATION.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
           })
    public User me(@AuthenticationPrincipal UserWrapper userWrapper){
        return repository.findById(userWrapper.getId()).get();
    }

    @GetMapping("getAll")
    @ApiOperation(value = "Get list of all users", response = List.class)
    @Secured("ROLE_TEACHER")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource")})
    @JsonView(View.FULL_INFORMATION.class)
    public List<User> getAllUsers(){
        return repository.findAll();
    }

    @GetMapping("/login/{login}")
    @ApiOperation(value = "Get user by login", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 400, message = "Invalid login")})
    @JsonView(View.FULL_INFORMATION.class)
    public User getByLogin(@PathVariable String login){
         return repository.findByLogin(login).orElseThrow(()->new NoSuchElementException("Cant find user with login = "+login));
    }
    @PatchMapping("/{login}")
    @ApiOperation(value = "Update user information", response = User.class)
    @PreAuthorize("#authComponentImpl.canUpdateUser(#login,#wrapper)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 400, message = "Invalid login")})
    @JsonView(View.FULL_INFORMATION.class)
    public User updateByLogin(UserUpdateRequest updateRequest,@PathVariable("login") String login,@AuthenticationPrincipal UserWrapper wrapper){
         return repository.findByLogin(login).orElseThrow(()->new NoSuchElementException("Cant find user with login = "+login));
    }
}
