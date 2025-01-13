package ru.solomka.market.controller.usecase.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.solomka.market.api.request.LoginRequest;
import ru.solomka.market.api.request.RegistrationRequest;
import ru.solomka.market.api.response.ApiObjectResponse;
import ru.solomka.market.api.response.JwtResponse;
import ru.solomka.market.repository.backet.BasketEntity;
import ru.solomka.market.repository.user.UserEntity;
import ru.solomka.market.repository.user.UserRepository;
import ru.solomka.market.secure.user.UserDetailsImpl;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthorizeController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @PostMapping(value = "/signin")
    public ResponseEntity<?> authenticateUser(@Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                userDetails.getUsername(),
                userDetails.getEmail()
        ));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid RegistrationRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiObjectResponse<>("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiObjectResponse<>("Error: Email is already in use!"));
        }

        UserEntity userEntity = UserEntity.builder()
                .username(signUpRequest.getUsername())
                .password(encoder.encode(signUpRequest.getPassword()))
                .email(signUpRequest.getEmail())
                .balance(0.0)
                .role("ROLE_USER")
                .basket(BasketEntity.builder().build())
                .build();

        userRepository.saveAndFlush(userEntity);

        return ResponseEntity.ok(new ApiObjectResponse<>("User registered successfully!"));
    }
}