package ru.solomka.market.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Service;
import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.repository.user.UserRepository;
import ru.solomka.market.secure.user.enums.UserPermission;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity getUserById(Long userId) {
        return userRepository.findById(userId).filter(user -> Objects.equals(user.getUserId(), userId))
                .orElseThrow(() -> new RuntimeException("User with id '%s' not found or is it not you".formatted(userId)));
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

        UserPermission userPermission;
        try {
            userPermission = UserPermission.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid user permission: " + role);
        }

        userEntity.setPermission(userPermission);
        return userRepository.saveAndFlush(userEntity);
    }

    @Override
    public UserEntity editUser(Long userId, String username, String email) {
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id '%s' not found".formatted(userId)));

        userEntity.setUsername((username == null || username.isEmpty()) ? userEntity.getUsername() : username);
        userEntity.setEmail((email == null || email.isEmpty()) ? userEntity.getEmail() : email);

        return userRepository.saveAndFlush(userEntity);
    }
}