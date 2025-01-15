package ru.solomka.market.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Product {
    private final String name, description;
    private final int price, quantity;
}