package ru.solomka.market.secure.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.List;

@Configuration
public class SecureFilters {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Bean
    public FilterRegistrationBean<?> loginRegistrationBean() {
        FilterRegistrationBean<PerAuthenticationFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new PerAuthenticationFilter(authenticationManager));
        filterRegistrationBean.setUrlPatterns(List.of("/api/v1/auth/**"));
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<?> allBean() {
        FilterRegistrationBean<AuthorizeTokenFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthorizeTokenFilter());
        filterRegistrationBean.setUrlPatterns(List.of("/**"));
        return filterRegistrationBean;
    }
}