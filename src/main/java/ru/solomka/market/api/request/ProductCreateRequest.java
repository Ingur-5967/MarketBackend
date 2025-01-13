package ru.solomka.market.api.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreateRequest {
    private final String name;
    private final String description;
    private final Integer price;
}
