package ru.solomka.market.mapper.schema;

import org.springframework.stereotype.Component;
import ru.solomka.market.api.User;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.user.UserEntity;

@Component
public class UserEntityUserMappingSchema implements SchemaMapping<UserEntity, User> {

    @Override
    public User map(UserEntity from) {
        return User.builder()
                .username(from.getUsername())
                .password(from.getPassword())
                .email(from.getEmail())
                .role(from.getPermission().getRole())
                .balance(from.getBalance())
                .build();
    }
}