package ru.solomka.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.Product;
import ru.solomka.market.api.response.ApiObjectResponse;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.service.basket.BasketService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/market/basket")
public class BasketController {

    private static final String ADD_BASKET_PRODUCT_BY_PRODUCT_ID = "/{userId}/product/add";
    private static final String GET_BASKET_PRODUCTS_BY_USER_ID = "/{userId}/product/all";
    private static final String DELETE_BASKET_PRODUCT_BY_PRODUCT_ID = "/{userId}/product/{productId}";

    private final BasketService basketService;

    @GetMapping(GET_BASKET_PRODUCTS_BY_USER_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public List<Product> getBasketProducts(@PathVariable("userId") Long userId) {
        List<ProductEntity> productEntities = basketService.getProductsByUserId(userId);

        return productEntities.stream()
                .map(Product.Factory::create)
                .toList();
    }

    @PostMapping(ADD_BASKET_PRODUCT_BY_PRODUCT_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public Product getBasketProducts(@PathVariable("userId") Long userId, @RequestParam("productId") Long productId) {
        return Product.Factory.create(basketService.addProductToBasket(userId, productId));
    }

    @DeleteMapping(DELETE_BASKET_PRODUCT_BY_PRODUCT_ID)
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseEntity<ApiObjectResponse<Boolean>> deleteProductFromBasket(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId) {
        return ResponseEntity.ok(new ApiObjectResponse<>(basketService.deleteProductByProductId(userId, productId)));
    }
}