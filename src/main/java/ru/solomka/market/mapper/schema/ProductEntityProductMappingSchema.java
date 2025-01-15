package ru.solomka.market.mapper.schema;

import org.springframework.stereotype.Component;
import ru.solomka.market.api.Product;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.product.ProductEntity;

@Component
public class ProductEntityProductMappingSchema implements SchemaMapping<ProductEntity, Product> {
    @Override
    public Product map(ProductEntity from) {
        return Product.builder()
                .name(from.getName())
                .description(from.getDescription())
                .price(from.getPrice())
                .quantity(from.getQuantity())
                .build();
    }
}