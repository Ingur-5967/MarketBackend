package ru.solomka.market.secure.user.enums;

import lombok.Getter;

import java.util.List;

@Getter
public enum UserPermission {

    USER(null, "ROLE_USER", List.of(

    )),
    SELLER(USER, "ROLE_SELLER", List.of(

    )),
    ADMIN(USER, "ROLE_ADMIN", List.of(

    )),
    MANAGER(ADMIN, "ROLE_MANAGER", List.of(

    ));

    private final String roleName;
    private final List<String> permissions;
    private final UserPermission child;

    UserPermission(UserPermission child, String roleName, List<String> permissions) {
        this.roleName = roleName;
        this.child = child;
        this.permissions = permissions;
    }

    public List<String> getPermissions() {
        UserPermission childRole = this.getChild();
        if(childRole == null)
            return this.permissions;
        else {
            this.permissions.addAll(childRole.getPermissions());
            return this.permissions.stream().distinct().toList();
        }
    }
}