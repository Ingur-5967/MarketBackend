package ru.solomka.market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.Product;
import ru.solomka.market.api.request.ProductCreateRequest;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.repository.product.ProductRepository;
import ru.solomka.market.service.product.ProductService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private static final String CREATE_PRODUCT = "/products/new";
    private static final String GET_PRODUCT_BY_NAME = "/products";
    private static final String GET_PRODUCT_BY_ID = "/products/{id}";

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping(GET_PRODUCT_BY_NAME)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getProductByParamName(@RequestParam("productName") String productName) {
        return Product.Factory.create(productService.getProductByProductName(productName));
    }

    @GetMapping(GET_PRODUCT_BY_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getProductByParamName(@PathVariable("id") Long productId) {
        return Product.Factory.create(productService.getProductByProductId(productId));
    }

    @PostMapping(CREATE_PRODUCT)
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER')")
    public Product createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        if(productCreateRequest.getName().isEmpty() || productCreateRequest.getDescription().isEmpty())
            throw new IllegalArgumentException("Product name or description cannot be empty");

        if(productCreateRequest.getPrice() < 0)
            throw new IllegalArgumentException("Product price cannot be negative");

        ProductEntity entity = ProductEntity.builder()
                .name(productCreateRequest.getName())
                .description(productCreateRequest.getDescription())
                .price(productCreateRequest.getPrice())
                .build();

        productRepository.saveAndFlush(entity);

        return Product.Factory.create(entity);
    }
}