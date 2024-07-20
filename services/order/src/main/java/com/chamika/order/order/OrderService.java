package com.chamika.order.order;

import com.chamika.order.exception.OrderProcessingException;
import com.chamika.order.exception.ResourceNotFoundException;
import com.chamika.order.customer.CustomerClient;
import com.chamika.order.customer.CustomerResponseBody;
import com.chamika.order.orderline.OrderLineService;
import com.chamika.order.product.ProductClient;
import com.chamika.order.product.ProductPurchaseRequestBody;
import com.chamika.order.product.ProductPurchaseResponseBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;


    private final OrderMapper orderMapper;  // TODO: is needed ?


    // TODO - add transactional

    // TODO: Break this function into smaller functions
//    public Integer createOrder(OrderCreateReqBody orderCreateReqBody) {
//
//        // 1) check whether the customer exists - need to interact with the customer service 😊
//        CustomerResponseBody customerResponseBody;
//
//        try {
//            customerResponseBody = customerClient
//                    .getCustomerById(orderCreateReqBody.customerId())
//                    .orElseThrow(
//                            () -> new ResourceNotFoundException("Customer with id " + orderCreateReqBody.customerId() + " does not exist.")
//                    );
//        } catch (Exception e) {
//            throw new OrderProcessingException("Failed to retrieve customer information (order --> customer): " + e.getMessage());
//        }
//
//
//        // 2)  purchase the products - need to interact with the product service 😊
//
//        // creating the list with productPurchaseRequests from the orderCreateReqBody
//        List<ProductPurchaseRequestBody> productPurchaseRequests = orderCreateReqBody
//                .products()
//                .stream()
//                .map(product -> new ProductPurchaseRequestBody(product.productId(), product.quantity()))
//                .toList();
//
//        List<ProductPurchaseResponseBody> purchasedProducts;
//
//        try {
//            purchasedProducts = productClient.purchaseProducts(productPurchaseRequests);
//        } catch (Exception e) {
//            throw new OrderProcessingException("Failed to purchase products: (order --> product)" + e.getMessage());
//        }
//
//        // 3) create the order & save
//        Order order = orderRepository.save(orderMapper.mapToOrder(orderCreateReqBody));
//
//        // 4) create order-line & save
//        for (ProductPurchaseRequestBody productPurchaseRequestBody : productPurchaseRequests) {
////           orderLineService.
//        }
//
//
//        // payment process - need to interact with the payment service 😊
//
//
//        // * send order-confirmation email - need to send message to Kafka Message Broker --> async
//
//
//    }
}