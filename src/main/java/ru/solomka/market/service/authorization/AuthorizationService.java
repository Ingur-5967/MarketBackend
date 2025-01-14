package ru.solomka.market.service.authorization;

import ru.solomka.market.repository.user.UserEntity;

public interface AuthorizationService {
    void authenticate(String username, String password);
    UserEntity registration(String username, String password, String email);
}