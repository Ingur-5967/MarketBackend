package ru.solomka.market;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.solomka.jwt.SecureLoader;

@SpringBootApplication
public class MarketBackendApplication implements SecureLoader {

    public static void main(String[] args) {
        SpringApplication.run(MarketBackendApplication.class, args);
    }

}
