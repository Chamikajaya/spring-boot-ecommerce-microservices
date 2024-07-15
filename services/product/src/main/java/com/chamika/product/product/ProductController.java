package com.chamika.product.product;

import com.chamika.product.product.shared.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Integer> createProduct(
            @RequestBody @Valid ProductCreateReqBody request
    ) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PostMapping("/purchase")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ProductPurchaseResponseBody>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequestBody> productPurchaseRequestBody) {
        return ResponseEntity.ok(productService.purchaseProduct(productPurchaseRequestBody));
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductResponseBody> getProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PageResponse<ProductResponseBody>> getAllProducts(
            @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(name = "size", defaultValue = "8", required = false) Integer size
    ) {
        return ResponseEntity.ok(productService.getAllProducts(page, size));
    }















}
