package ru.solomka.market.controller.rest.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.User;
import ru.solomka.market.api.request.user.LoginRequest;
import ru.solomka.market.api.request.user.RegistrationRequest;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.service.authorization.AuthorizationService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizeController {

    private final AuthorizationService authorizationService;
    private final SchemaMapping<UserEntity, User> userMapping;

    @PostMapping("/signin")
    @PreAuthorize("!isAuthenticated()")
    public void authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        authorizationService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PostMapping("/signup")
    @PreAuthorize("!isAuthenticated()")
    public User registerUser(@Valid @RequestBody RegistrationRequest signUpRequest) {
        UserEntity registeredUser = authorizationService
                .registration(signUpRequest.getUsername(), signUpRequest.getPassword(), signUpRequest.getEmail());

        return userMapping.map(registeredUser);
    }
}