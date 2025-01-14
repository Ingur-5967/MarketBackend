package ru.solomka.market.service.pricing;

import org.springframework.stereotype.Service;
import ru.solomka.market.api.Basket;

@Service
public class PricingServiceImpl implements PricingService {
    @Override
    public Integer getPriceByBasket(Basket basket) {
        return basket.getProducts().stream()
                .mapToInt(product -> (product.getPrice() * product.getQuantity()))
                .sum();
    }
}