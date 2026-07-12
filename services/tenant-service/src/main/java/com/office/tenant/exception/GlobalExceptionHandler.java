package com.office.tenant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TenantNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleTenantNotFound(TenantNotFoundException ex) 
    {
        Map<String, Object> error = new HashMap<>();

        error.put("timestamp",LocalDateTime.now());

        error.put("status",404);

        error.put("message",ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(DuplicateTenantException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateTenant(DuplicateTenantException ex) 
    {

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp",LocalDateTime.now());

        error.put("status",409);

        error.put("message",ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) 
    {

        Map<String, Object> error = new HashMap<>();

        error.put("timestamp",LocalDateTime.now());

        error.put("status",500);

        error.put("message",ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult()
            .getFieldErrors()
            .forEach(error ->
                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    )
            );

    return new ResponseEntity<>(
            errors,
            HttpStatus.BAD_REQUEST
    );
}
}