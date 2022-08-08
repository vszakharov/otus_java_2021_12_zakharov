package ru.otus.auth.service;

/**
 * Сервис для генерации токенов.
 */
public interface TokenService {

    String generateToken(String clientId);
}
