package ru.solomka.market.api.request.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCreateRequest {
    @Size(min = 1, max = 25)
    @NotBlank
    private final String name;

    @Size(min = 10, max = 100)
    @NotBlank
    private final String description;

    @Positive
    @NotBlank
    private final Integer price;
}