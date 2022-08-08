package ru.otus.auth;

public interface TokenService {

    boolean checkToken(String token);
    String getClientIdFromToken(String token);
}
