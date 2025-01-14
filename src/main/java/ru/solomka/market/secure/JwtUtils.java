package ru.solomka.market.secure.utils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import ru.solomka.jwt.config.ConfigurationImpl;
import ru.solomka.jwt.secure.SecureManager;
import ru.solomka.market.MarketBackendApplication;
import ru.solomka.market.secure.user.UserDetailsImpl;

import java.util.Date;
import java.util.Map;

public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static String generateToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        Date now = new Date();
        return Jwts.builder().subject("UserToken").claims(Map.of("username", userPrincipal.getUsername()))
                .issuedAt(now)
                .expiration(new Date(now.getTime() + getJwtExpiredTime()))
                .signWith(Keys.hmacShaKeyFor(getJwtSecret().getBytes())).compact();
    }

    public static String getUsernameFromJwtToken(String token) {
        SecureManager secureManager = new SecureManager(getJwtSecret());
        return secureManager.validator()
                .validateKey(token)
                .map(obj -> String.valueOf(obj.getPayload().get("username")))
                .orElseThrow(() -> new RuntimeException("Invalid token format"));
    }

    public static boolean validateJwtToken(String token) {
        try {
            return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(getJwtSecret().getBytes()))
                    .build().parseSignedClaims(token)
                    .getPayload() != null;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public static String getJwtSecret() {
        ConfigurationImpl configuration = new ConfigurationImpl(MarketBackendApplication.class);
        return String.valueOf(configuration.getValue("application.properties", "market.backend.jwtSecret")
                .orElseThrow(() -> new RuntimeException("Unknown error")));
    }

    public static long getJwtExpiredTime() {
        ConfigurationImpl configuration = new ConfigurationImpl(MarketBackendApplication.class);
        return Long.parseLong(String.valueOf(configuration.getValue("application.properties", "market.backend.jwtExpirationMs")
                .orElseThrow(() -> new RuntimeException("Unknown error"))));
    }
}