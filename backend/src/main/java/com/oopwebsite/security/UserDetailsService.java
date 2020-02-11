package com.oopwebsite.security;

import com.oopwebsite.entity.User;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.wrappers.UserWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(s)
                .orElseThrow(()->new BadCredentialsException("No such user!"));
        return UserWrapper.create(user);
    }

    @Transactional
    public UserDetails loadUserById(String id){
        User user = userRepository.findById(id).orElseThrow(()->
                new UsernameNotFoundException("User not found with id "+id));
        return UserWrapper.create(user);
    }
}
