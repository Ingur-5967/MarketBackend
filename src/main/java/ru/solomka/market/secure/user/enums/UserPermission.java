package ru.solomka.market.secure.user.enums;

import lombok.Getter;

@Getter
public enum UserPermission {

    USER("ROLE_USER"),
    SELLER("ROLE_SELLER"),
    ADMIN("ROLE_ADMIN"),
    MANAGER("ROLE_MANAGER");

    private final String role;

    UserPermission(String role) {
        this.role = role;
    }
}
