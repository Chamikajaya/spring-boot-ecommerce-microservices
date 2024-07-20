package com.chamika.order.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleException(ResourceNotFoundException e, HttpServletRequest request) {

        log.error("Resource not found exception occurred", e);

        ApiError apiError = new ApiError(
                request.getRequestURL().toString(),
                e.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(OrderProcessingException.class)
    public ResponseEntity<ApiError> handleException(OrderProcessingException e, HttpServletRequest request) {

        log.error("OrderProcessingException  occurred", e);

        ApiError apiError = new ApiError(
                request.getRequestURL().toString(),
                e.getMessage(),
                null,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("Method Argument not valid Exception occurred", e);

        List<String> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                "Validation failed",
                errors,
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }



    // * Catch all exceptions handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e, HttpServletRequest request) {

        log.error("Unhandled Exception Occurred ", e);

        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
