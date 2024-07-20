package com.chamika.order.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-lines")
@RequiredArgsConstructor
public class OrderLineController {

    private final OrderLineService orderLineService;

    @GetMapping("/order/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<OrderLineResponse>> findAllOrderLinesRelatedToAnOrder(
            @PathVariable Integer orderId
    ) {
        return ResponseEntity.ok().body(orderLineService.findAllOrderLinesRelatedToAnOrder(orderId));
    }

}
