package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Chowo
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
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