package ru.solomka.market.controller.rest.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.Product;
import ru.solomka.market.api.response.ApiObjectResponse;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.service.basket.BasketService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/market/basket")
public class BasketController {

    private static final String ADD_BASKET_PRODUCT_BY_PRODUCT_ID = "/{userId}/product/add";
    private static final String GET_BASKET_PRODUCTS_BY_USER_ID = "/{userId}/product/all";
    private static final String DELETE_BASKET_PRODUCT_BY_PRODUCT_ID = "/{userId}/product/{productId}";

    private final BasketService basketService;
    private final SchemaMapping<ProductEntity, Product> productMapping;

    @GetMapping(GET_BASKET_PRODUCTS_BY_USER_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public List<Product> getBasketProducts(@PathVariable("userId") Long userId) {
        Map<ProductEntity, Integer> productEntities = basketService.getProductsByUserId(userId);
        return productEntities.entrySet().stream().map(entry -> {
            ProductEntity productEntity = entry.getKey();
            Integer quantity = entry.getValue();
            return new Product(
                    productEntity.getName(),
                    productEntity.getDescription(),
                    productEntity.getPrice(),
                    quantity
            );
        }).toList();
    }

    @PostMapping(ADD_BASKET_PRODUCT_BY_PRODUCT_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getBasketProducts(@PathVariable("userId") Long userId, @RequestParam("productId") Long productId) {
        ProductEntity addedProductEntity = basketService.addProductToBasket(userId, productId);
        return productMapping.map(addedProductEntity);
    }

    @DeleteMapping(DELETE_BASKET_PRODUCT_BY_PRODUCT_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<ApiObjectResponse<Boolean>> deleteProductFromBasket(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(new ApiObjectResponse<>(basketService.deleteProductByProductId(userId, productId)));
    }
}