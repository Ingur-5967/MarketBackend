package ru.solomka.market.api.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    @Size(min = 5, max = 20)
    @NotBlank
    private final String username;

    @Size(min = 6, max = 15)
    @NotBlank
    private final String password;

    @Email
    @NotBlank
    private final String email;
}