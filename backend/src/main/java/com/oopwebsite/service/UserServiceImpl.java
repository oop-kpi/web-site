package com.oopwebsite.service;

import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.controller.exceptions.UserAlreadyExistsException;
import com.oopwebsite.dto.LoginRequest;
import com.oopwebsite.dto.SignUpRequest;
import com.oopwebsite.dto.UserUpdateRequest;
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

import java.util.HashSet;
import java.util.Set;

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
    public User updateUser(UserUpdateRequest request) {
        User user = mapToDao(request);
        userRepository.save(user);
        return user;

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
        user.setRoles(Set.of(Role.ROLE_USER));
        return user;
    }
    private User mapToDao(UserUpdateRequest updateRequest){
        User prev = userRepository.findById(updateRequest.getId()).orElseThrow(()->new NoSuchElementException("Cant find user with id = "+updateRequest.getId()));
        User user = new User();
        Set<Role> roles = new HashSet<>();
        user.setId(prev.getId());
        user.setLogin(prev.getLogin());
        user.setName(updateRequest.getName() == null? prev.getName():updateRequest.getName());
        user.setEmail(updateRequest.getEmail() == null? prev.getEmail():updateRequest.getEmail());
        user.setGroup(updateRequest.getGroup() == null? prev.getGroup():Group.valueOf(updateRequest.getGroup()));
        user.setBall(updateRequest.getBall() == null? prev.getBall(): Integer.parseInt(updateRequest.getBall()));
        if (updateRequest.getRoles() != null) {
            for (String role : (updateRequest.getRoles())) {
                roles.add(Role.valueOf(role));
            }
        }
            roles.add(Role.ROLE_USER);
            user.setRoles(roles);
        user.setPassword(updateRequest.getPassword() == null? prev.getPassword(): passwordEncoder.encode(updateRequest.getPassword()));
        return user;
    }
}
