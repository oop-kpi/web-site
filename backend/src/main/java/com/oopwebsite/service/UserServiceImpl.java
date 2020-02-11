package com.oopwebsite.service;

import com.oopwebsite.controller.exceptions.UserAlreadyExistsException;
import com.oopwebsite.dto.LoginRequest;
import com.oopwebsite.dto.SignUpRequest;
import com.oopwebsite.entity.Group;
import com.oopwebsite.entity.Role;
import com.oopwebsite.entity.User;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.security.jwt.JwtTokenProvider;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService{
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider tokenProvider;
    private AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public String login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLogin(),
                        loginRequest.getPassword()
                )
        );
        return tokenProvider.createToken(authentication);
    }
    @Override
    public void registerUser(SignUpRequest registerRequest) throws UserAlreadyExistsException {
        if(userRepository.existsByLogin(registerRequest.getLogin())) {
            throw new UserAlreadyExistsException("Login address already in use.");
        }
        userRepository.save(mapToDao(registerRequest));
    }

    private User mapToDao(SignUpRequest registerRequest){
        User user = modelMapper.map(registerRequest, User.class);
        user.setGroup(Group.valueOf(registerRequest.getGroup()));
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRoles(Collections.singletonList(Role.ROLE_USER));
        return user;
    }
}
