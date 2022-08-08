package ru.otus.auth.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenServiceImpl implements TokenService {
    private static final int TOKEN_TTL = 5;
    private final String secretKey;

    public TokenServiceImpl(String secretKey) {
        this.secretKey = secretKey;
    }


    @Override
    public String generateToken(String clientId) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);

        Instant now = Instant.now();
        Instant exp = now.plus(TOKEN_TTL, ChronoUnit.HOURS);

        return JWT.create()
                .withIssuer("authService")
                .withAudience("server")
                .withSubject(clientId)
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(exp))
                .sign(algorithm);
    }
}
