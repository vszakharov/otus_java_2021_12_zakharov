package ru.otus.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.auth.repository.ClientRepository;
import ru.otus.auth.service.ClientService;
import ru.otus.auth.service.ClientServiceImpl;
import ru.otus.auth.service.TokenService;
import ru.otus.auth.service.TokenServiceImpl;

@Configuration
public class Config {

    @Bean
    public ClientService clientService(
            ClientRepository clientRepository
    ) {
        return new ClientServiceImpl(clientRepository);
    }

    @Bean
    public TokenService tokenService(
            @Value("${auth.jwt.secret}")String secretKey
    ) {
        return new TokenServiceImpl(secretKey);
    }
}
