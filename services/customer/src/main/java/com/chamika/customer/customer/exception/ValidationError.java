package com.chamika.customer.customer.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ValidationError(
        String path,
        List<String> errors,
        int statusCode,
        LocalDateTime timestamp
) {}