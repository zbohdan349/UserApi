package com.example.demo.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<String> handleUserException(BadUserCreation exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleNotFoundException(UserNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler
    public ResponseEntity<String> handleUpdateException(EmailException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDateException(DateException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
