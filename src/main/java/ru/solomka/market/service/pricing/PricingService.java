package ru.solomka.market.service.pricing;

import ru.solomka.market.api.Basket;
import ru.solomka.market.api.Product;

public interface PricingService {
    Integer getPriceByBasket(Basket basket);
}