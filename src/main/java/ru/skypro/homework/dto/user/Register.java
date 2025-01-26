package ru.skypro.homework.dto.user;

import lombok.Data;
import ru.skypro.homework.dto.Role;


/**
 * Класс регистрации нового пользователя.
 * <p>
 * Класс {@code Register} используется для создания аккаунта и авторизации пользователя,
 * включая логин (username), пароль (password), имя (firstName), фамилию (lastName), номер телефона (phone) и его роль (role).
 * </p>
 */
@Data
public class Register {

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    private String phone;

    private Role role;
}
