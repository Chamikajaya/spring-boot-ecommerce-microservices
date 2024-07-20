package com.chamika.order.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        String path,
        String message,
        List<String> errors,

        int statusCode,
        LocalDateTime timestamp
) {
}