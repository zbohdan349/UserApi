package com.example.demo.controller;

import com.example.demo.model.UserCreateReq;
import com.example.demo.model.User;
import com.example.demo.model.UserUpdateReq;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(UserCreateReq userReq){
        userService.save(userReq);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Set<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User>updateFields(@PathVariable Integer id, UserUpdateReq req){
        userService.update(req,id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<User>deleteUserById(@PathVariable Integer id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/birthdate_range")
    public ResponseEntity<Set<User>> getUsersByBirthDate(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                @RequestParam LocalDate from,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                @RequestParam LocalDate to){
        return ResponseEntity.ok(userService.getUsersByBirthDateRange(from,to));
    }



}
