package com.chamika.order.product;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "product",
        url = "${application.config.product-url}"
)
public interface ProductClient {

    @PostMapping("/purchase")
    List<ProductPurchaseResponseBody> purchaseProducts(
            @RequestBody List<ProductPurchaseRequestBody> productPurchaseRequestBody
    );

}
