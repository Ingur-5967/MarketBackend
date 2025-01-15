package ru.solomka.market.mapper.schema;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.solomka.market.api.Basket;
import ru.solomka.market.api.Product;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.product.ProductEntity;

@Component
@RequiredArgsConstructor
public class BasketEntityBasketMappingSchema implements SchemaMapping<BasketEntity, Basket> {

    private final SchemaMapping<ProductEntity, Product> productMapping;

    @Override
    public Basket map(BasketEntity from) {
        return Basket.builder()
                .ownerId(from.getUserId())
                .products(from.getProducts().keySet().stream().map(productMapping::map).toList())
                .build();
    }
}