package com.oopwebsite.security.authcomponents;

import com.oopwebsite.entity.LaboratoryWork;
import com.oopwebsite.entity.Role;
import com.oopwebsite.entity.User;
import com.oopwebsite.repository.LaboratoryWorkRepository;
import com.oopwebsite.repository.UserRepository;
import com.oopwebsite.wrappers.UserWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthComponentImplTest {
    @Mock
    UserRepository repository;
    @Mock
    LaboratoryWorkRepository laboratoryWorkRepository;
    @Before
    public void setMockRepos(){
        MockitoAnnotations.initMocks(this);

    }
    @Test
    public void canModify() {
        User user = new User();
        user.setId("someid");
        User user2 = new User();
        user2.setId("someid2");
        LaboratoryWork laboratoryWork = new LaboratoryWork();
        laboratoryWork.setUser(user);
        LaboratoryWork laboratoryWork2 = new LaboratoryWork();
        laboratoryWork2.setUser(user2);

        User illegalUser = new User();

        doReturn(Optional.of(user)).when(repository).findById("someid");
        doReturn(Optional.of(user2)).when(repository).findById("someid2");
        doReturn(Optional.of(illegalUser)).when(repository).findById("illegalUser");
        doReturn(Optional.of(laboratoryWork)).when(laboratoryWorkRepository).findById("lab1");
        doReturn(Optional.of(laboratoryWork2)).when(laboratoryWorkRepository).findById("lab2");

        AuthComponentImpl authComponent = new AuthComponentImpl(repository,laboratoryWorkRepository);
        UserWrapper someid = new UserWrapper("someid", "", "", "", "", Collections.singleton(Role.ROLE_USER));
        boolean lab1 = authComponent.canModify("lab1", someid,0);
        UserWrapper someid2 = new UserWrapper("illegalUser", "", "", "", "", Collections.singleton(Role.ROLE_USER));
        boolean lab2 = authComponent.canModify("lab2", someid2,15);
        boolean lab3 = authComponent.canModify("lab1", someid,4);
        assertTrue(lab1);
        assertFalse(lab2);
        assertFalse(lab3);
    }
}