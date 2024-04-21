package com.example.final_boss_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Anotación para indicar que esta excepción se traduce en un estado HTTP 404 Not Found
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    // Constructor que acepta un mensaje personalizado para la excepción
    public DataNotFoundException(String message) {
        super(message);
    }
}