package ru.solomka.market.secure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.solomka.market.secure.AuthenticationEntryPointJWT;
import ru.solomka.market.secure.filter.AuthorizeTokenFilter;
import ru.solomka.market.secure.filter.PerAuthenticationFilter;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class HttpSecureConfiguration {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthorizeTokenFilter authorizeTokenFilter,
                                           AuthenticationProvider authenticationProvider,
                                           AuthenticationEntryPointJWT unauthorizedHandler,
                                           AuthenticationManager authenticationManager) throws Exception {
        http
                .addFilterAfter(authorizeTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(new PerAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/**").permitAll().anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider);

        return http.build();
    }
}