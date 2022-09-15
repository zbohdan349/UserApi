package com.example.demo.exception;

public class BadUserCreation extends RuntimeException{

    public BadUserCreation(String message) {
        super(message);
    }
}
