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

import java.util.*;
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


    /**
     * This method handles the product purchase process.
     * It checks whether the purchase can proceed, updates the stock of the products,
     * and returns the ProductPurchaseResponseBody.
     *
     * @param productPurchaseRequestBody List of products to be purchased
     * @return List of ProductPurchaseResponseBody
     */
    @Transactional(rollbackFor = ProductPurchaseException.class)
    // * If any error occurs during the purchase process, all changes made to the database will be undone, maintaining data consistency. (ATOM)
    public List<ProductPurchaseResponseBody> purchaseProduct(
            List<ProductPurchaseRequestBody> productPurchaseRequestBody
    ) {
        // Fetch and validate all products mentioned in the request
        Map<Integer, Product> productDictionary = fetchAndValidateProducts(productPurchaseRequestBody);

        // Process each purchase request and return the response
        return processPurchaseRequests(productPurchaseRequestBody, productDictionary);
    }

    /**
     * Fetches all products mentioned in the purchase request from the database
     * and validates their existence.
     *
     * @param productPurchaseRequestBody List of products to be purchased
     * @return Map of product IDs to Product objects
     * @throws ProductPurchaseException if any requested product is not found
     */

    private Map<Integer, Product> fetchAndValidateProducts(List<ProductPurchaseRequestBody> productPurchaseRequestBody) {
        // Extract all product IDs from the request
        Set<Integer> allMentionedProdIds = productPurchaseRequestBody.stream()
                .map(ProductPurchaseRequestBody::productId)
                .collect(Collectors.toSet());

        // Fetch all mentioned products from the database
        List<Product> allProductsToBePurchased = productRepository.findAllById(allMentionedProdIds);

        // Get the IDs of all valid products found in the database
        Set<Integer> idsOfAllValidProducts = allProductsToBePurchased.stream()
                .map(Product::getId)
                .collect(Collectors.toSet());

        // Check if all requested products exist in the database
        if (idsOfAllValidProducts.size() != allMentionedProdIds.size()) {
            Set<Integer> invalidProductIds = new HashSet<>(allMentionedProdIds);
            invalidProductIds.removeAll(idsOfAllValidProducts);
            throw new ProductPurchaseException(
                    "Purchase could not proceed because the following product ids were not found: " + invalidProductIds
            );
        }

        // Create and return a map of product IDs to Product objects (map -> productId : product)
        return allProductsToBePurchased.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));
    }

    /**
     * Processes each purchase request, updates product stock,
     * and creates response bodies.
     *
     * @param productPurchaseRequestBody List of products to be purchased
     * @param productDictionary Map of product IDs to Product objects
     * @return List of ProductPurchaseResponseBody
     * @throws ProductPurchaseException if there's not enough stock for a product
     */
    private List<ProductPurchaseResponseBody> processPurchaseRequests(
            List<ProductPurchaseRequestBody> productPurchaseRequestBody,
            Map<Integer, Product> productDictionary
    ) {
        List<ProductPurchaseResponseBody> responseBodies = new ArrayList<>();

        // List to store all updated products - to cater for stock updates which happen in the loop below :)
        List<Product> updatedProducts = new ArrayList<>();

        for (ProductPurchaseRequestBody requestBody : productPurchaseRequestBody) {
            // Get the product from the dictionary which is related to the current requested product
            Product product = productDictionary.get(requestBody.productId());

            // Check if there's enough stock for the purchase
            if (product.getQuantityInStock() < requestBody.quantity()) {
                throw new ProductPurchaseException(
                        "Not enough stock for product with id " + requestBody.productId()
                );
            }

            // Update the stock
            product.setQuantityInStock(product.getQuantityInStock() - requestBody.quantity());
            updatedProducts.add(product);

            // Create and add the response body
            responseBodies.add(new ProductPurchaseResponseBody(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    requestBody.quantity(),  // Quantity purchased by the user - take it from the request body not from the product object
                    product.getUnitPrice()
            ));
        }

        // Save all updated products to the database - due to stock updates
        productRepository.saveAll(updatedProducts);

        return responseBodies;
    }

}
