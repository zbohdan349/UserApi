package com.example.demo.service;

import com.example.demo.exception.BadUserCreation;

import com.example.demo.model.User;
import com.example.demo.model.UserCreateReq;
import com.example.demo.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService userService;
    @Mock
    UserRepository userRepository;

    private UserCreateReq userCreateReq;

    private User user;

    @BeforeEach
    public void setup(){

        userCreateReq = new UserCreateReq();
        userCreateReq.setEmail("bo@gmail.com");
        userCreateReq.setFirstName("name");
        userCreateReq.setLastName("last_name");
        userCreateReq.setBirthday(LocalDate.of(2000,1,1));

        user = new User();
        user.setEmail("bo@gmail.com");
        user.setFirstName("name");
        user.setLastName("last_name");
        user.setBirthday(LocalDate.of(2000,1,1));
    }

    @Test
    public void saveWhenUserExistThenException(){
        Mockito.when(userRepository.isUserExist(Mockito.any())).thenReturn(true);
        assertThrows(BadUserCreation.class,()->{
            userService.save(userCreateReq);
        });
    }
    @Test
    public void saveWhenUserNotExistSuccessfulCreation(){
        Mockito.when(userRepository.isUserExist(Mockito.any())).thenReturn(false);
        assertTrue(userService.save(userCreateReq));
    }

}
