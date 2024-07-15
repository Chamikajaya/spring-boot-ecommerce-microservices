package com.chamika.product.product;


import com.chamika.product.product.exception.ProductPurchaseException;
import com.chamika.product.product.exception.ResourceNotFoundException;
import com.chamika.product.product.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Integer createProduct(ProductCreateReqBody request) {
        Product product = productMapper.toProduct(request);
        return productRepository.save(product).getId();
    }


    @Transactional(rollbackFor = ProductPurchaseException.class)  // *  if any error occurs during the purchase process, all changes made to the database will be undone, maintaining data consistency. (ATOM)

    public List<ProductPurchaseResponseBody> purchaseProduct(
            List<ProductPurchaseRequestBody> productPurchaseRequestBody
    ) {

        // grab all product ids from request body
        Set<Integer> allMentionedProdIds = productPurchaseRequestBody
                .stream()
                .map(ProductPurchaseRequestBody::productId)
                .collect(Collectors.toSet());

        // fetching the mentioned products from db
        List<Product> allProductsToBePurchased = productRepository.findAllById(allMentionedProdIds);

        // ids of all valid products
        Set<Integer> idsOfAllValidProducts = allProductsToBePurchased
                .stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        // checking whether all the products requested for purchase exists in our db
        if (idsOfAllValidProducts.size() != allMentionedProdIds.size()) {
            // get the ids of all invalid products
            allMentionedProdIds.removeAll(idsOfAllValidProducts);
            throw new ProductPurchaseException(
                    "Purchase could not be proceed ahead because the following product ids were not found [ " + allMentionedProdIds + " ]"
            );
        }

        // creating a map (productId : product)
        Map<Integer, Product> productDictionary = allProductsToBePurchased.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));


        // response bodies to be returned
        List<ProductPurchaseResponseBody> responseBodies = new ArrayList<>();

        // processing each purchase for each product mentioned - by looping through the request of the user
        for (ProductPurchaseRequestBody requestBody : productPurchaseRequestBody) {

            Product product = productDictionary.get(requestBody.productId());

            // checking whether there is enough stock
            if (product.getQuantityInStock() < requestBody.quantity()) {
                throw new ProductPurchaseException(
                        "Not enough stock for product with id " + requestBody.productId()
                );
            }

            // TODO: will do price calculation in other service

            // stock update
            product.setQuantityInStock(product.getQuantityInStock() - requestBody.quantity());

            // building the productPurchaseResponse
            responseBodies.add(
                    new ProductPurchaseResponseBody(
                            product.getId(),
                            product.getName(),
                            product.getDescription(),
                            requestBody.quantity(),
                            product.getUnitPrice()

                    ));


        }

        // saving the products (because their quantity was updated)
        productRepository.saveAll(allProductsToBePurchased);

        return responseBodies;
    }

    public ProductResponseBody getProductById(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + productId + " not found !")
                );

        return productMapper.toProductResponse(product);

    }

    public PageResponse<ProductResponseBody> getAllProducts(Integer page, Integer size) {
        // creating the pageable object
        Pageable pageable = PageRequest.of(
                page,
                size
        );

        Page<Product> products = productRepository.findAll(pageable);

        List<ProductResponseBody> productResponseBodies = products.stream()
                .map(productMapper::toProductResponse)
                .toList();

        return new PageResponse<>(
                productResponseBodies,
                products.getNumber(),
                products.getSize(),
                products.getTotalElements(),
                products.getTotalPages(),
                products.isFirst(),
                products.isLast()
        );

    }
}
