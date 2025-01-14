package ru.solomka.market.secure.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.util.ContentCachingRequestWrapper;
import ru.solomka.market.secure.utils.JwtUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


public class PerAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private static final String BAD_CREDENTIAL_MESSAGE = "Authentication failed for username: %s and password: %s";

    private static final Logger log = LoggerFactory.getLogger(PerAuthenticationFilter.class);

    private final AuthenticationManager authenticationManager;

    public PerAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/v1/auth/signin", "POST"));
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws AuthenticationException {

        ContentCachingRequestWrapper request = new ContentCachingRequestWrapper(servletRequest);


        Map<String, Object> mappedParameters;
        try {
            mappedParameters = new JSONParser(String.join(" ", request.getReader().lines().toList())).object();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }


        String username = null;
        String password = null;
        try {
            username = String.valueOf(mappedParameters.get("username"));
            password = String.valueOf(mappedParameters.get("password"));

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authenticationToken);
        }
        catch (AuthenticationException e) {
            log.error(String.format(BAD_CREDENTIAL_MESSAGE, username, password), e);
            throw e;
        }
        catch (Exception e) {
            servletResponse.setStatus(INTERNAL_SERVER_ERROR.value());
            Map<String, String> error = new HashMap<>();
            error.put("errorMessage", e.getMessage());
            servletResponse.setContentType(APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(servletResponse.getOutputStream(), error);
            throw new RuntimeException(String.format("Error in attemptAuthentication with username %s and password %s", username, password), e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authentication) {

        String token = JwtUtils.generateToken(authentication);

        Cookie cookie = new Cookie("UserToken", token);

        cookie.setPath("/");
        cookie.setMaxAge(86400);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);

    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> error = new HashMap<>();
        error.put("errorMessage", "Bad credentials");
        response.setContentType(APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(), error);
    }
}