package com.tickets.ticket_service.exception;

import com.tickets.ticket_service.domain.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
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
                        error -> {
                            String message = error.getDefaultMessage();
                            return message != null ? message : ""; // Replace null with empty string
                        },
                        // If there are multiple errors for the same field, keep the last one
                        (a, b) -> b
                ));

        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        400,
                        "/api/tickets",
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
                        "/api/tickets",
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
                        "/api/tickets",
                        "INTERNAL_SERVER_ERROR",
                        ex.getMessage(),
                        null
                )
        );
    }

}
