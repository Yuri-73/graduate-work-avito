package ru.skypro.homework.exception;

import org.springframework.security.access.AccessDeniedException;

public class IncorrectPasswordException extends RuntimeException {
    private final String username;

    public IncorrectPasswordException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format("Старый пароль для пользователя'%s' не корректный!", username);
    }
}