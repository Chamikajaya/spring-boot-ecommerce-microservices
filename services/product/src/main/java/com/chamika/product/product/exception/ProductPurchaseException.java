package com.chamika.product.product.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProductPurchaseException extends RuntimeException{
    public ProductPurchaseException(String message) {
        super(message);
    }

}
