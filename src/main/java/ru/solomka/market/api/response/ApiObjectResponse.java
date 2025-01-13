package ru.solomka.market.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiObjectResponse<T> {
    private final T data;
}
