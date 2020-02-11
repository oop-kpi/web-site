package com.oopwebsite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.entity.User;
import com.oopwebsite.entity.view.View;
import com.oopwebsite.repository.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {
    private UserRepository repository;

    public UserController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/login/{login}")
    @JsonView(View.FULL_INFORMATION.class)
    public User getByLogin(@PathVariable String login){
         return repository.findByLogin(login).orElseThrow(()->new NoSuchElementException("Cant find user with login = "+login));
    }
}
