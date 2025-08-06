package com.tickets.api_gateway.exception;


import com.tickets.api_gateway.domain.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Обрабатывает ошибки валидации (например, некорректные данные в запросе).
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(MethodArgumentNotValidException ex) {
        log.warn("Validation error: {}", ex.getMessage());
        Map<String, String> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        error -> error.getDefaultMessage() != null ? error.getDefaultMessage() : "Invalid value",
                        (a, b) -> b
                ));

        return ResponseEntity.badRequest().body(
                ApiResponse.error(
                        400,
                        "/api",
                        "BAD_REQUEST",
                        "Validation failed",
                        errors
                )
        );
    }

    /**
     * Обрабатывает ошибки доступа (например, недостаточно прав).
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<String>> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("Access denied: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ApiResponse.error(
                        403,
                        "/api",
                        "FORBIDDEN",
                        "Access denied: " + ex.getMessage(),
                        null
                )
        );
    }

    /**
     * Обрабатывает ошибки от WebClient (например, 4xx или 5xx от downstream-сервисов).
     */
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiResponse<String>> handleWebClientResponseException(WebClientResponseException ex) {
        log.error("WebClient error: {} - {}", ex.getStatusCode(), ex.getMessage());
        HttpStatus status = (HttpStatus) ex.getStatusCode();
        String errorCode = status.is4xxClientError() ? "CLIENT_ERROR" : "SERVER_ERROR";
        return ResponseEntity.status(status).body(
                ApiResponse.error(
                        status.value(),
                        "/api",
                        errorCode,
                        ex.getMessage(),
                        null
                )
        );
    }

    /**
     * Обрабатывает все необработанные исключения.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected error: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ApiResponse.error(
                        500,
                        "/api",
                        "INTERNAL_SERVER_ERROR",
                        "An unexpected error occurred",
                        null
                )
        );
    }
}
