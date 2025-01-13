package ru.solomka.market.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.User;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.service.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String GET_USER = "/api/v1/users";

    private final UserService userService;

    @GetMapping(GET_USER)
    @PostAuthorize("isAuthenticated() && authentication.principal.username == returnObject.username")
    public User getUserById(@RequestParam("userId") Long userId) {
        UserEntity userEntity = userService.getUserById(userId);
        return User.Factory.create(userEntity);
    }
}