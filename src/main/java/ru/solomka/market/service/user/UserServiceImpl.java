package ru.solomka.market.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.repository.user.UserRepository;
import ru.solomka.market.secure.user.enums.UserPermission;

import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id '%s' not found".formatted(userId)));
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email '%s' not found".formatted(email)));
    }

    @Override
    public BasketEntity getBasketById(Long userId) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id '%s' not found".formatted(userId)));

        return userEntity.getBasket();
    }

    @Override
    public UserEntity setUserRole(Long userId, String role) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id '%s' not found".formatted(userId)));


        UserPermission permission = Arrays.stream(UserPermission.values())
                .filter(userPermission -> Objects.equals(userPermission.getRole(), role))
                .findAny().orElseThrow(() -> new RuntimeException("Role '%s' not found".formatted(role)));

        userEntity.setPermission(permission);

        return userRepository.saveAndFlush(userEntity);
    }
}
