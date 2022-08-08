package ru.otus.auth.exception;

public class LoginException extends RuntimeException {

    public LoginException(String message) {
        super(message);
    }
}
