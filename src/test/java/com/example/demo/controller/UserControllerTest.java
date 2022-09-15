package com.example.demo.controller;

import com.example.demo.exception.BadUserCreation;
import com.example.demo.exception.DateException;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.model.User;
import com.example.demo.model.UserCreateReq;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void getSuccessfulUserById() throws Exception {
        User user =new User();
        user.setId(1);

        given(service.getUserById(1)).willReturn(user);
        mvc.perform(get("/users/"+1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void unsuccessfulUserById() throws Exception {
        User user =new User();
        user.setId(1);

        given(service.getUserById(1)).willThrow(UserNotFoundException.class);
        mvc.perform(get("/users/"+1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void deleteUser() throws Exception {
        User user =new User();
        user.setId(1);

        given(service.deleteUserById(1)).willReturn(true);
        mvc.perform(delete("/users/"+1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        given(service.deleteUserById(1)).willThrow(UserNotFoundException.class);
        mvc.perform(delete("/users/"+1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    @Test
    public void updateUser() throws Exception {
        User user = new User();
        user.setId(1);

        given(service.update(Mockito.any(), Mockito.any())).willReturn(true);
        mvc.perform(put("/users/" + 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void successfulUserCreation() throws Exception {
        UserCreateReq req =new UserCreateReq();

        given(service.save(req)).willReturn(true);
        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    public void getAllUsers() throws Exception {
        given(service.getAllUsers()).willReturn(new HashSet<User>());
        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    public void getUsersByBirthDate() throws Exception {
        given(service.getUsersByBirthDateRange(
                LocalDate.of(2000,1,1),
                LocalDate.of(2002,2,2))).
                willReturn(new HashSet<User>());
        mvc.perform(get("/users/birthdate_range?from=2000-01-01&to=2002-02-02")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());


    }
    @Test
    public void unsuccessfulGetUsersByBirthDate() throws Exception {
        given(service.getUsersByBirthDateRange(
                LocalDate.of(2002,1,1),
                LocalDate.of(2000,2,2))).
                willThrow(DateException.class);
        mvc.perform(get("/users/birthdate_range?from=2002-01-01&to=2000-02-02")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

}
