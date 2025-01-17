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
}