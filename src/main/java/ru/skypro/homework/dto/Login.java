package ru.skypro.homework.dto;

import lombok.Data;



/**
 * Класс для представления данных входа пользователя.
 * <p>
 * Класс {@code Login} используется для передачи данных входа пользователя, включая
 * логин (username) и пароль (password). Эти данные используются для аутентификации
 * пользователя в системе.
 * </p>
 */
@Data
public class Login {

    private String username;

    private String password;

}
