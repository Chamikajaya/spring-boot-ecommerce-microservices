package com.chamika.order.exception;

import java.time.LocalDateTime;

public record APIError(
        String path,
        String message,
        int statusCode,
        LocalDateTime timestamp
) {
}