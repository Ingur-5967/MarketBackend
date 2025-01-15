package ru.solomka.market.controller.rest.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.Product;
import ru.solomka.market.api.request.ProductCreateRequest;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.service.product.ProductService;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private static final String CREATE_PRODUCT = "/products/new";
    private static final String GET_PRODUCT_BY_ID = "/products/find/{productId}";
    private static final String GET_PRODUCT_BY_NAME = "/products/find";

    private final ProductService productService;
    private final SchemaMapping<ProductEntity, Product> productMapping;

    @GetMapping(GET_PRODUCT_BY_NAME)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getProductByParamName(@RequestParam("productName") String productName) {
        ProductEntity productEntityByName = productService.getProductByProductName(productName);
        return productMapping.map(productEntityByName);
    }

    @GetMapping(GET_PRODUCT_BY_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getProductByParamId(@PathVariable("productId") Long productId) {
        ProductEntity productEntityById = productService.getProductByProductId(productId);
        return productMapping.map(productEntityById);
    }

    @PostMapping(CREATE_PRODUCT)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        ProductEntity productEntity = productService.createProduct(
                productCreateRequest.getName(),
                productCreateRequest.getDescription(),
                productCreateRequest.getPrice()
        );

        return productMapping.map(productEntity);
    }
}