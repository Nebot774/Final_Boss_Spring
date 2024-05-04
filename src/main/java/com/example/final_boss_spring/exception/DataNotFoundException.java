package com.example.final_boss_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends RuntimeException {
    // Constructor que acepta un mensaje personalizado para la excepción
    public DataNotFoundException(String message) {
        super(message);
    }

    // Nuevo constructor que acepta tanto un mensaje como una causa
    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}