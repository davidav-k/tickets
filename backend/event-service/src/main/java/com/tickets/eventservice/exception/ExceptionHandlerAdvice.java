package com.tickets.eventservice.exception;

import com.tickets.eventservice.domain.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(java.util.stream.Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "",
                        (a, b) -> b
                ));

        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        400,
                        "/api/events",
                        "BAD_REQUEST",
                        "Validation failed",
                        errors
                )
        );
    }

    @ExceptionHandler(TicketServiceException.class)
    public ResponseEntity<ApiResponse<String>> handleTicketServiceException(TicketServiceException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.error(
                        400,
                        "/api/events",
                        "TICKET_SERVICE_ERROR",
                        ex.getMessage(),
                        null
                )
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(
                        500,
                        "/api/events",
                        "INTERNAL_SERVER_ERROR",
                        ex.getMessage(),
                        null
                )
        );
    }
}