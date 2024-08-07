package com.chamika.order.order;

import com.chamika.order.exception.OrderProcessingException;
import com.chamika.order.exception.ResourceNotFoundException;
import com.chamika.order.customer.CustomerClient;
import com.chamika.order.customer.CustomerResponseBody;
import com.chamika.order.kafka.OrderConfirmation;
import com.chamika.order.kafka.OrderNotificationProducer;
import com.chamika.order.orderline.OrderLineRequest;
import com.chamika.order.orderline.OrderLineService;
import com.chamika.order.payment.PaymentClient;
import com.chamika.order.payment.PaymentRequestBody;
import com.chamika.order.product.ProductClient;
import com.chamika.order.product.ProductPurchaseRequestBody;
import com.chamika.order.product.ProductPurchaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;  // using Feign Client
    private final ProductClient productClient;  // using Feign Client
    private final PaymentClient paymentClient;  // using Feign Client
    private final OrderLineService orderLineService;
    private final OrderMapper orderMapper;

    private final OrderNotificationProducer orderProducer;


    public List<OrderResponse> findAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::mapToOrderResponse)
                .toList();
    }

    public OrderResponse findOrderById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(orderMapper::mapToOrderResponse)
                .orElseThrow(() -> new ResourceNotFoundException("Order with id " + orderId + " does not exist."));
    }


    @Transactional
    public Integer createOrder(OrderCreateReqBody orderCreateReqBody) {

        // 1) check whether the customer exists - need to interact with the customer service 😊
        CustomerResponseBody customerResponseBody;

        try {
            customerResponseBody = customerClient
                    .getCustomerById(orderCreateReqBody.customerId())
                    .orElseThrow(
                            () -> new ResourceNotFoundException("Customer with id " + orderCreateReqBody.customerId() + " does not exist.")
                    );
        } catch (Exception e) {
            throw new OrderProcessingException("Failed to retrieve customer information (order --> customer): " + e.getMessage());
        }

        // 2)  purchase the products - need to interact with the product service 😊

        // creating the list with productPurchaseRequests from the orderCreateReqBody
        List<ProductPurchaseRequestBody> productPurchaseRequests = orderCreateReqBody
                .products()
                .stream()
                .map(product -> new ProductPurchaseRequestBody(product.productId(), product.quantity()))
                .toList();

        List<ProductPurchaseResponseBody> purchasedProducts;

        try {
            // calling the product service to purchase the products and get the response
            purchasedProducts = productClient.purchaseProducts(productPurchaseRequests);
        } catch (Exception e) {
            throw new OrderProcessingException("Failed to purchase products: (order --> product)" + e.getMessage());
        }


        // * Calculate the total amount
        BigDecimal totalAmount = calculateTotalAmount(purchasedProducts);

        // 3) create the order & save
        Order order = orderRepository.save(orderMapper.mapToOrder(orderCreateReqBody, totalAmount));

        // 4) create order-line & save
        for (ProductPurchaseRequestBody productPurchaseRequestBody : productPurchaseRequests) {
            orderLineService.createOrderLine(
                    new OrderLineRequest(
                            order.getId(),
                            productPurchaseRequestBody.productId(),
                            productPurchaseRequestBody.quantity()
                    )
            );
        }

        // interacting with payment service
        paymentClient.requestOrderPayment(
                new PaymentRequestBody(
                        null,
                        totalAmount,
                        orderCreateReqBody.paymentMethod(),
                        order.getId(),
                        order.getReference(),
                        customerResponseBody
                )
        );

        // * send order-confirmation email - need to send message to Kafka Message Broker
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        order.getReference(),
                        totalAmount,
                        orderCreateReqBody.paymentMethod(),
                        customerResponseBody,
                        purchasedProducts
                )
        );

        return order.getId();
    }

    // Helper method to calculate the total amount
    private BigDecimal calculateTotalAmount(List<ProductPurchaseResponseBody> purchasedProducts) {
        return purchasedProducts.stream()
                .map(product -> product.price().multiply(BigDecimal.valueOf(product.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}
