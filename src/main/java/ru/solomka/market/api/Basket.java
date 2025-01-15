package ru.solomka.market.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class Basket {

    private final Long ownerId;
    private final List<Product> products;

//    public static class Factory {
//        public static Basket create(BasketEntity basketEntity) {
//            return new Basket(
//                    basketEntity.getUserId(),
//                    basketEntity.getProducts()
//                            .keySet().stream()
//                            .map(obj -> new Product(obj.getName(), obj.getDescription(), obj.getPrice(), obj.getQuantity()))
//                            .toList()
//            );
//        }
//    }
}