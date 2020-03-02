package com.oopwebsite.security.authcomponents;

import com.oopwebsite.controller.exceptions.NoSuchElementException;
import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.Role;
import com.oopwebsite.entity.User;
import com.oopwebsite.repository.LaboratoryWorkRepository;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.wrappers.UserWrapper;
import org.springframework.stereotype.Component;

@Component
public class AuthComponentImpl implements AuthComponent {
    private UserRepository userRepository;
    private LaboratoryWorkRepository laboratoryWorkRepository;

    public AuthComponentImpl(UserRepository userRepository, LaboratoryWorkRepository laboratoryWorkRepository) {
        this.userRepository = userRepository;
        this.laboratoryWorkRepository = laboratoryWorkRepository;
    }

    @Override
    public boolean canModify(String labId, UserWrapper wrapper, int ball) {
        User user = userRepository.findById(wrapper.getId()).get();
        if(!(user.getRoles().contains(Role.ROLE_TEACHER))&& ball!=0){
            return false;
        }
        LaboratoryWork laboratoryWork = laboratoryWorkRepository.findById(labId).orElseThrow(() -> new NoSuchElementException("Cant find lab with id " + labId));
        if (user.getRoles().contains(Role.ROLE_TEACHER)){ return true;}
        if (laboratoryWork.getUser().equals(user)) {return true; } else {return false;}
    }


    @Override
    public boolean canUpdateUser(String login,UserWrapper wrapper) {
        User currentUser = userRepository.findById(wrapper.getId()).get();
        User user = userRepository.findByLogin(login).orElseThrow(()->new NoSuchElementException("Cant find user with login = "+login));
        if (currentUser.getRoles().contains(Role.ROLE_TEACHER)){ return true;}
        if (currentUser.equals(user)) {return true; } else {return false;}
    }

}
