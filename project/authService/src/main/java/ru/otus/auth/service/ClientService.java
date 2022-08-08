package ru.otus.auth.service;

/**
 * Сервис для регистрации клиентов и проверки их прав.
 */
public interface ClientService {

    void register(String clientId, String clientSecret);
    void checkCredentials(String clientId, String clientSecret);
}
