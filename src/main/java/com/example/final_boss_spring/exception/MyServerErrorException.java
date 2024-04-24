package com.example.final_boss_spring.exception;

public class MyServerErrorException extends RuntimeException {
    public MyServerErrorException(String message) {
        super(message);
    }
}