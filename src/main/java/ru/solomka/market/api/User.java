package ru.solomka.market.api;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.solomka.market.repository.user.UserEntity;

@Data
@AllArgsConstructor
public class User {

    private final String username;

    @JsonIgnore
    private final String password;

    private final String email;
    private final String role;
    private final double balance;

    public static class Factory {
        public static User create(UserEntity userEntity) {
            return new User(
                    userEntity.getUsername(),
                    userEntity.getPassword(),
                    userEntity.getEmail(),
                    userEntity.getRole(),
                    userEntity.getBalance()
            );
        }
    }
}