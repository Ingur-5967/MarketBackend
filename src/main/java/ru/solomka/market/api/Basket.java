package ru.solomka.market.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.solomka.market.repository.backet.BasketEntity;

import java.util.List;

@Data
@AllArgsConstructor
public class Basket {

    private final Long ownerId;
    private final List<Product> products;

    public static class Factory {
        public static Basket create(BasketEntity basketEntity) {
            return new Basket(
                    basketEntity.getUserId(),
                    basketEntity.getProducts()
                            .keySet().stream()
                            .map(obj -> new Product(obj.getName(), obj.getDescription(), obj.getPrice(), obj.getQuantity()))
                            .toList()
            );
        }
    }
}