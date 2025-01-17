package ru.solomka.market.service.user;

import ru.solomka.market.repository.user.UserEntity;

public interface UserService {
    UserEntity getUserByUserId(Long userId);
    UserEntity getUserByEmail(String email);

    UserEntity setUserRole(Long userId, String role);

    UserEntity editUserBio(Long userId, String username, String email);
}