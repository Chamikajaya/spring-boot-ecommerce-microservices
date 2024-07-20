package com.chamika.order.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        return ResponseEntity.ok().body(orderService.findAllOrders());
    }

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<OrderResponse> findOrderById(
            @PathVariable Integer orderId
    ) {
        return ResponseEntity.ok().body(orderService.findOrderById(orderId));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Integer> createOrder(
            @RequestBody @Valid OrderCreateReqBody orderCreateReqBody
    ) {
        return ResponseEntity.ok().body(orderService.createOrder(orderCreateReqBody));
    }


}
