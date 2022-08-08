package ru.otus.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.auth.dto.TokenResponseDto;
import ru.otus.auth.dto.UserDto;
import ru.otus.auth.service.ClientService;
import ru.otus.auth.service.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final ClientService clientService;
    private final TokenService tokenService;

    public AuthController(ClientService clientService, TokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDto user) {
        clientService.register(user.clientId(), user.clientSecret());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/token")
    public TokenResponseDto getToken(@RequestBody UserDto user) {
        clientService.checkCredentials(user.clientId(), user.clientSecret());

        return new TokenResponseDto(tokenService.generateToken(user.clientId()));
    }


}
