package ru.solomka.market.secure.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.solomka.market.mapper.SchemaMapping;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.repository.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    private final SchemaMapping<UserEntity, UserDetailsImpl> userDetailsMapping;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User[%s] not found".formatted(username)));

        return userDetailsMapping.map(userEntity);
    }

}