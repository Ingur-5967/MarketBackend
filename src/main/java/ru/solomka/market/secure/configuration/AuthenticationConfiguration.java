package ru.solomka.market.secure.configuration;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.solomka.market.secure.AuthenticationEntryPointJWT;
import ru.solomka.market.secure.filter.AuthorizeTokenFilter;

@Configuration
public class AuthenticationConfiguration {

    @Bean
    public DaoAuthenticationProvider authenticationProvider(@NotNull UserDetailsService userDetailsService,
                                                            @NotNull PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(passwordEncoder);

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setHideUserNotFoundExceptions(false);

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthorizeTokenFilter authorizeTokenFilter() {
        return new AuthorizeTokenFilter();
    }

    @Bean
    public AuthenticationEntryPointJWT authenticationEntryPointJWT() {
        return new AuthenticationEntryPointJWT();
    }
}