package ru.solomka.market.service.product;

import ru.solomka.market.repository.product.ProductEntity;

public interface ProductService {
    ProductEntity getProductByProductId(Long productId);
    ProductEntity getProductByProductName(String productName);
}