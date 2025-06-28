package com.tickets.ticket_service.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

@Schema(description = "Generic API response structure")
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        String time,
        int code,
        String path,
        String status,
        String message,
        T data
) {
    @Contract("_, _, _ -> new")
    public static <T> @NotNull ApiResponse<T> success(String path, String message, T data) {
        return new ApiResponse<>(
                Instant.now().toString(),
                200,
                path,
                "OK",
                message,
                data
        );
    }

    @Contract("_, _, _, _, _ -> new")
    public static <T> @NotNull ApiResponse<T> error(int code, String path, String status, String message, T data) {
        return new ApiResponse<>(
                Instant.now().toString(),
                code,
                path,
                status,
                message,
                data
        );
    }
}
