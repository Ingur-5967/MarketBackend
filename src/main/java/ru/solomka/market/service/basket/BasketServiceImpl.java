package ru.solomka.market.service.basket;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.backet.BasketRepository;
import ru.solomka.market.repository.product.ProductEntity;
import ru.solomka.market.service.product.ProductService;
import ru.solomka.market.service.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;

    private final UserService userRepository;
    private final ProductService productService;

    @Override
    public ProductEntity addProductToBasket(Long userId, Long productId) {
        BasketEntity basketEntity = basketRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User basket with id '%s' not found".formatted(userId)));

        ProductEntity productEntity = productService.getProductByProductId(productId);

        List<ProductEntity> products = basketEntity.getProducts();
        products.add(productEntity);
        basketEntity.setProducts(new ArrayList<>(products));

        if(basketEntity.getUser() == null)
            basketEntity.setUser(userRepository.getUserById(userId));

        basketRepository.saveAndFlush(basketEntity);

        return productEntity;
    }

    @Override
    public ProductEntity getProductByProductId(Long userId, Long productId) {
        BasketEntity basketEntity = basketRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("User basket with id '%s' not found".formatted(userId)));

        return basketEntity.getProducts().stream().filter(product -> Objects.equals(product.getProductId(), productId))
                .findAny().orElseThrow(() -> new RuntimeException("Product with id '%s' not found".formatted(productId)));
    }

    @Override
    public ProductEntity getProductByProductName(Long userId, String productName) {
        BasketEntity basketEntity = basketRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User basket with id '%s' not found".formatted(userId)));

        return basketEntity.getProducts().stream().filter(product -> Objects.equals(productName, product.getName()))
                .findAny().orElseThrow(() -> new RuntimeException("Product with name '%s' not found".formatted(productName)));
    }

    @Override
    public boolean deleteProductByProductId(Long userId, Long productId) {
        BasketEntity basketEntity = basketRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User basket with id '%s' not found".formatted(userId)));

        ProductEntity productEntity = basketEntity.getProducts().stream()
                .filter(product -> Objects.equals(product.getProductId(), productId)).findAny()
                .orElseThrow(() -> new RuntimeException("Product in basket[%s] with id '%s' not found".formatted(userId, productId)));

        basketEntity.getProducts().remove(productEntity);

        basketRepository.saveAndFlush(basketEntity);

        return true;
    }

    @Override
    public List<ProductEntity> getProductsByUserId(Long userId) {
        BasketEntity basketEntity = basketRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User basket with id '%s' not found".formatted(userId)));

        return basketEntity.getProducts();
    }
}