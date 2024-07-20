package com.chamika.payment.payment;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Integer> createPayment(@RequestBody @Valid PaymentCreateReqBody paymentCreateReqBody) {
        return ResponseEntity.ok().body(paymentService.createPayment(paymentCreateReqBody));
    }



}
