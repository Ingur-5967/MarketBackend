package ru.solomka.market.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.solomka.market.repository.product.ProductEntity;

@Data
@AllArgsConstructor
public class Product {

    private final String name, description;
    private final int price;

    public static class Factory {
        public static Product create(ProductEntity productEntity) {
            return new Product(productEntity.getName(), productEntity.getDescription(), productEntity.getPrice());
        }
    }
}