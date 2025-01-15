package ru.solomka.market.controller.rest.v1;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.User;
import ru.solomka.market.api.request.EditBioRequest;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.service.user.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String GET_USER = "/api/v1/users/{userId}";
    private static final String EDIT_CONTACT_USER = "/api/v1/users/{userId}/bio/edit";
    private static final String EDIT_ROLE_USER = "/api/v1/users/{userId}/role/edit";

    private final UserService userService;
    private final SchemaMapping<UserEntity, User> userMapping;

    @GetMapping(GET_USER)
    @PostAuthorize("isAuthenticated()")
    public User getUserById(@PathVariable("userId") Long userId) {
        UserEntity userEntity = userService.getUserById(userId);
        return userMapping.map(userEntity);
    }

    @PostMapping(EDIT_ROLE_USER)
    @PostAuthorize("isAuthenticated() && hasAuthority('ROLE_MANAGER')")
    public User setUserRole(@PathVariable("userId") Long userId, @RequestBody String role) {
        UserEntity userEntity = userService.setUserRole(userId, role.toUpperCase());
        return userMapping.map(userEntity);
    }

    @PostMapping(EDIT_CONTACT_USER)
    @PreAuthorize("isAuthenticated()")
    public User editUserContact(@PathVariable("userId") Long userId,
                                @Valid @RequestBody EditBioRequest editBioRequest) {

        UserEntity userEntity = userService.editUser(userId, editBioRequest.getUsername(), editBioRequest.getEmail());
        return userMapping.map(userEntity);
    }
}