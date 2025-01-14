package ru.solomka.market.service.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solomka.market.api.Product;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.repository.product.ProductRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public ProductEntity getProductByProductId(Long productId) {
        Optional<ProductEntity> productEntity = productRepository.findById(productId);
        return productEntity.orElseThrow(() -> new RuntimeException("Product with id '%s' not found".formatted(productId)));
    }

    @Override
    public ProductEntity getProductByProductName(String productName) {
        Optional<ProductEntity> productEntity = productRepository.findByName(productName);
        return productEntity.orElseThrow(() -> new RuntimeException("Product with name '%s' not found".formatted(productName)));
    }

    @Override
    public ProductEntity createProduct(String productName, String productDescription, Integer price) {
        ProductEntity entity = ProductEntity.builder()
                .name(productName)
                .description(productDescription)
                .price(price)
                .quantity(1)
                .build();

        return productRepository.saveAndFlush(entity);
    }
}