package ru.solomka.market.mapper.schema;

import org.springframework.stereotype.Component;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.secure.user.UserDetailsImpl;

@Component
public class UserEntityUserDetailsMappingSchema implements SchemaMapping<UserEntity, UserDetailsImpl> {

    @Override
    public UserDetailsImpl map(UserEntity from) {
        return new UserDetailsImpl(
                from.getUsername(),
                from.getPassword(),
                from.getEmail(),
                from.getRole().getRoleName(),
                from.getBalance()
        );
    }
}
