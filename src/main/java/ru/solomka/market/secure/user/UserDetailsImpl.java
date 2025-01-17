package ru.solomka.market.secure.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.solomka.market.repository.user.UserEntity;

import java.util.Collection;
import java.util.Collections;

@RequiredArgsConstructor
@Data
public class UserDetailsImpl implements UserDetails {

    private final String username;

    @JsonIgnore
    private final String password;

    private final String email;

    private final String role;

    private final double balance;

    public static UserDetailsImpl fromUserEntity(UserEntity userEntity) {
        return new UserDetailsImpl(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEmail(),
                userEntity.getRole().getRoleName(),
                userEntity.getBalance()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
