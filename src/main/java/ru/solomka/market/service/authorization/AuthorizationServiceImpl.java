package ru.solomka.market.service.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.repository.user.UserRepository;
import ru.solomka.market.secure.user.enums.UserPermission;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Override
    public void authenticate(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Override
    public UserEntity registration(String username, String password, String email) {
        if (userRepository.existsByUsername(username))
            throw new RuntimeException("Username '%s' is already in use".formatted(username));

        if (userRepository.existsByEmail(email))
            throw new RuntimeException("Email '%s' is already in use".formatted(email));

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(encoder.encode(password))
                .email(email)
                .balance(0.0)
                .role(UserPermission.USER)
                .basket(BasketEntity.builder().build())
                .build();

        return userRepository.saveAndFlush(userEntity);
    }
}