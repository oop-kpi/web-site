package com.oopwebsite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.entity.User;
import com.oopwebsite.entity.view.View;
import com.oopwebsite.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@Api("User information service")
@RequestMapping("api/user")
public class UserController {
    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("getAll")
    @ApiOperation(value = "Get list of all users", response = List.class)
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
}
