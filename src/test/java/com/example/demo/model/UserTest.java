package com.example.demo.model;

import com.example.demo.exception.BadUserCreation;
import com.example.demo.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void successfulValidationOfEmail(){
        User  user = new User();
        user.setEmail("user@domain.com");
        assertEquals("user@domain.com",user.getEmail());
    }

    @Test
    public void unsuccessfulValidationOfEmail(){
        User  user = new User();

        Assertions.assertThrows(BadUserCreation.class, () -> {
            user.setEmail("user.domain.com");
        });
        Assertions.assertThrows(BadUserCreation.class, () -> {
            user.setEmail("@user");
        });
    }
    @Test
    public void successfulDateCheck(){
        User  user = new User();
        user.setBirthday(LocalDate.of(2000,1,1));
        assertEquals(LocalDate.of(2000,1,1),user.getBirthday());
    }
    @Test
    public void unsuccessfulDateCheck(){
        User  user = new User();

        Assertions.assertThrows(BadUserCreation.class, () -> {
            user.setBirthday(LocalDate.of(2100,1,1));
        });
        Assertions.assertThrows(BadUserCreation.class, () -> {
            user.setBirthday(LocalDate.now());
        });
    }
}
