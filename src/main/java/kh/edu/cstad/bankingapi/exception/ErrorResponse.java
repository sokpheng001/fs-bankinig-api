package kh.edu.cstad.bankingapi.exception;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ErrorResponse<T>(
        String message,
        Integer status,
        LocalDateTime timestamp,
        T details
) { }
