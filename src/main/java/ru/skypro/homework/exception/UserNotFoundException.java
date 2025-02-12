package ru.skypro.homework.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    private final String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }

    @Override
    public String getMessage() {
        return String.format("Пользователь: '%s' не найден!", username);
    }
}
