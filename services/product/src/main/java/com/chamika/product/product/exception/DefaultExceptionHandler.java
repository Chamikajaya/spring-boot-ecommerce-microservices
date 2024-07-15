package com.chamika.product.product.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIError> handleException(ResourceNotFoundException e, HttpServletRequest request) {

        log.error("Resource not found exception occurred", e);

        APIError apiError = new APIError(
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(ProductPurchaseException.class)
    public ResponseEntity<APIError> handleException(ProductPurchaseException e, HttpServletRequest request) {

        log.error("Resource not found exception occurred", e);

        APIError apiError = new APIError(
                request.getRequestURL().toString(),
                e.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);

    }


    // * Catch all exceptions handler
    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIError> handleException(Exception e, HttpServletRequest request) {

        log.error("Unhandled Exception Occurred ", e);

        APIError apiError = new APIError(
                request.getRequestURI(),
                e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
