package ru.solomka.market.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.Product;
import ru.solomka.market.api.request.ProductCreateRequest;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.service.product.ProductService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private static final String CREATE_PRODUCT = "/products/new";
    private static final String GET_PRODUCT_BY_OPTION = "/products";

    private final ProductService productService;

    @GetMapping(GET_PRODUCT_BY_OPTION)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getProductByParamName(@RequestParam("option") String productName) {
        return Product.Factory.create(productService.getProductByProductName(productName));
    }

    @GetMapping(GET_PRODUCT_BY_OPTION)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getProductByParamName(@RequestParam("option") Long productId) {
        return Product.Factory.create(productService.getProductByProductId(productId));
    }

    @PostMapping(CREATE_PRODUCT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        ProductEntity productEntity = productService.createProduct(
                productCreateRequest.getName(),
                productCreateRequest.getDescription(),
                productCreateRequest.getPrice()
        );

        return Product.Factory.create(productEntity);
    }
}