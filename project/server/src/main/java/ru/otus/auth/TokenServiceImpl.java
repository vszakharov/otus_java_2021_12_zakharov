package ru.otus.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TokenServiceImpl implements TokenService {
    private static final Logger log = LoggerFactory.getLogger(TokenServiceImpl.class);
    private final String secretKey;

    public TokenServiceImpl(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean checkToken(String token) {
        try {
            DecodedJWT decodedJWT = decodeToken(token);
            if (!decodedJWT.getIssuer().equals("authService")) {
                log.error("Issuer is incorrect");
                return false;
            }

            if (!decodedJWT.getAudience().contains("server")) {
                log.error("Audience is incorrect");
                return false;
            }
        } catch (JWTVerificationException e) {
            log.error("Token is invalid: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public String getClientIdFromToken(String token) {
        DecodedJWT decodedJWT = decodeToken(token);
        return decodedJWT.getSubject();
    }

    private DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();

        return verifier.verify(token);
    }
}
