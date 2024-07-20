package com.chamika.order.orderline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    public void createOrderLine(OrderLineRequest orderLineRequest) {
        OrderLine orderLine = orderLineMapper.toOrderLine(orderLineRequest);
        orderLineRepository.save(orderLine);
    }

    public List<OrderLineResponse> findAllOrderLinesRelatedToAnOrder(Integer orderId) {
        return orderLineRepository.findAllByOrderId(orderId)
                .stream()
                .map(orderLineMapper::toOrderLineResponse)
                .toList();
    }


}
