package ru.solomka.market.service.basket;

import ru.solomka.market.repository.product.ProductEntity;

import java.util.List;

public interface BasketService {
    ProductEntity addProductToBasket(Long userId, Long productId);
    ProductEntity getProductByProductId(Long userId, Long productId);
    ProductEntity getProductByProductName(Long userId, String productName);
    boolean deleteProductByProductId(Long userId, Long productId);

    List<ProductEntity> getProductsByUserId(Long userId);


}