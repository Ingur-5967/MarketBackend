package ru.solomka.market.api.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequest {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;
}