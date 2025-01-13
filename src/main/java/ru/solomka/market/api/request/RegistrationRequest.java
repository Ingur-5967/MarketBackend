package ru.solomka.market.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationRequest {
    private final String username;

    @JsonIgnore
    private final String password;
    private final String email;
}
