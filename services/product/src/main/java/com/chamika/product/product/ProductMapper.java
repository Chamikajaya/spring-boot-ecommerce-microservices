package com.chamika.product.product;

import com.chamika.product.category.Category;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {
    public Product toProduct(ProductCreateReqBody productCreateReqBody) {
        return Product.builder()
                .name(productCreateReqBody.name())
                .description(productCreateReqBody.description())
                .quantityInStock(productCreateReqBody.quantityInStock())
                .unitPrice(productCreateReqBody.unitPrice())
                .category(
                        Category
                                .builder()
                                .id(productCreateReqBody.categoryId())
                                .build()
                )
                .build();
    }

    public ProductResponseBody toProductResponse(Product product) {
        return new ProductResponseBody(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getQuantityInStock(),
                product.getUnitPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()

        );
    }
}
