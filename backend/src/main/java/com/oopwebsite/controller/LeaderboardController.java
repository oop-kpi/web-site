package com.oopwebsite.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.oopwebsite.dto.LeaderboardDto;
import com.oopwebsite.entity.User;
import com.oopwebsite.entity.view.View;
import com.oopwebsite.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {
    private UserRepository userRepository;

    public LeaderboardController(UserRepository repository){
        this.userRepository = repository;
    }
    @GetMapping
    @ApiOperation(value = "Get leaderboard", response = LeaderboardDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully ")})
    public Collection<LeaderboardDto> getList(){
        return userRepository.findAll().stream().sorted(((user, user2) -> Integer.valueOf(user2.getBall()).compareTo(user.getBall()))).map(user ->
            new LeaderboardDto(user.getName(),user.getBall(),user.getGroup().name(),
                    user.getLaboratoryWorks().stream().filter(Objects::nonNull).filter(laboratoryWork -> laboratoryWork.getMark()>0).collect(Collectors.toList()).size())).
                collect(Collectors.toList());


    }
}
