package com.example.demo.service;

import com.example.demo.exception.BadUserCreation;
import com.example.demo.exception.EmailException;
import com.example.demo.model.User;
import com.example.demo.model.UserUpdateReq;
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
public class MappingUtilsTest {

    @InjectMocks
    private MappingUtils mappingUtils;
    @Mock
    private UserRepository userRepository;

    private User user;

    private UserUpdateReq req;

    @BeforeEach
    public void setup(){
        user = new User();
        req =new UserUpdateReq();

        user.setEmail("bo@gmail.com");
        user.setFirstName("name");
        user.setLastName("last_name");
        user.setBirthday(LocalDate.of(2000,1,1));

        req.setEmail("other@gmail.com");
        req.setFirstName("otherName");
        req.setLastName(null);
        req.setBirthday(LocalDate.of(1999,1,1));
    }


    @Test
    public void mappingSuccessfulTest(){
        Mockito.when(userRepository.isUserExist(Mockito.any())).thenReturn(false);

        user = mappingUtils.map(req,user);

        assertEquals(user.getBirthday(), req.getBirthday());
        assertEquals(user.getFirstName(),req.getFirstName());

        assertEquals(user.getLastName(),user.getLastName());

        assertEquals(user.getEmail(),req.getEmail());
    }

    @Test
    public void mappingUnSuccessfulTestWhenDateIsNotCorrect(){
        Mockito.when(userRepository.isUserExist(Mockito.any())).thenReturn(false);

        req.setBirthday(LocalDate.of(2020,1,1));

        assertThrows(BadUserCreation.class,()->{
            user = mappingUtils.map(req,user);
        });
    }

    @Test
    public void mappingUnSuccessfulTestWhenUserExist(){
        Mockito.when(userRepository.isUserExist(Mockito.any())).thenReturn(true);

        assertThrows(EmailException.class,()->{
            user = mappingUtils.map(req,user);
        });
    }


}
