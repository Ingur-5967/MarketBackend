package ru.solomka.market.service.user;

import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.user.UserEntity;

public interface UserService {
    UserEntity getUserById(Long userId);
    UserEntity getUserByEmail(String email);
    BasketEntity getBasketById(Long userId);

    UserEntity setUserRole(Long userId, String role);

    UserEntity editUser(Long userId, String username, String email);
}