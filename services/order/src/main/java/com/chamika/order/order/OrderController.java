package com.chamika.order.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderCreateReqBody orderCreateReqBody
    ) {
        return ResponseEntity.ok().body(orderService.createOrder(orderCreateReqBody));
    }


}
