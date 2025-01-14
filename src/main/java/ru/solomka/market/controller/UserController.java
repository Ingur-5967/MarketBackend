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

    private static final String GET_USER = "/api/v1/users/{userId}";
    private static final String SET_ROLE_USER = "/api/v1/users/{userId}/set-role";

    private final UserService userService;

    @GetMapping(GET_USER)
    @PostAuthorize("isAuthenticated() && authentication.principal.username == returnObject.username")
    public User getUserById(@PathVariable("userId") Long userId) {
        UserEntity userEntity = userService.getUserById(userId);
        return User.Factory.create(userEntity);
    }

    @PostMapping(SET_ROLE_USER)
    @PostAuthorize("isAuthenticated()")
    public User setUserRole(@PathVariable("userId") Long userId, @RequestParam("role") String role) {
        UserEntity userEntity = userService.setUserRole(userId, role.toUpperCase());
        return User.Factory.create(userEntity);
    }
}