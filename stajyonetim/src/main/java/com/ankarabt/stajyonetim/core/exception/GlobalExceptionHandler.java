package com.ankarabt.stajyonetim.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {


        Map<String, Object> feedback = new HashMap<>();
        feedback.put("zaman", LocalDateTime.now());
        feedback.put("durum", "BAŞARISIZ");
        feedback.put("mesaj", ex.getMessage());

        return new ResponseEntity<>(feedback, HttpStatus.BAD_REQUEST);
    }
}