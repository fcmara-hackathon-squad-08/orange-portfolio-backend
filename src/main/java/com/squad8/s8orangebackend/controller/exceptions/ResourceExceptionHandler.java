package com.squad8.s8orangebackend.controller.exceptions;

import com.squad8.s8orangebackend.service.exceptions.DatabaseException;
import com.squad8.s8orangebackend.service.exceptions.ResourceNotFoundException;
import com.squad8.s8orangebackend.service.exceptions.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        String error = "Resource not found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(Instant.now(), status.value(), error,
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> dataBaseException(DatabaseException e, HttpServletRequest request) {
        String error = "Database error";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(Instant.now(), status.value(), error,
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<StandardError> unauthorizedAccessException(UnauthorizedAccessException e, HttpServletRequest request) {
        String error = "UnauthorizedAccessException error";
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(Instant.now(), status.value(), error,
                e.getMessage(), request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}
