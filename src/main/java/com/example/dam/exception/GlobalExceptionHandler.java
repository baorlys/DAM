package com.example.dam.exception;

import com.example.dam.global.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleIOException(Exception e, HttpServletRequest request) {
        log.error(e.getMessage() + " " + request.getRequestURI());
        return ResponseEntity.badRequest().body(new ApiError(e.getMessage(),request.getRequestURI()));
    }


}
