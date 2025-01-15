package ru.solomka.market.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class User {
    private final String username;
    @JsonIgnore
    private final String password;
    private final String email;
    @JsonIgnore
    private final String role;
    private final double balance;
}