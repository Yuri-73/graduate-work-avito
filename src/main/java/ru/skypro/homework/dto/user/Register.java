package ru.skypro.homework.dto.user;

import lombok.Data;
import ru.skypro.homework.dto.Role;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


/**
 * Класс регистрации нового пользователя.
 * <p>
 * Класс {@code Register} используется для создания аккаунта и авторизации пользователя,
 * включая логин (username), пароль (password), имя (firstName), фамилию (lastName), номер телефона (phone) и его роль (role).
 * </p>
 * @author Yuri-73
 */
@Data
public class Register {

    @Size(min = 4, max = 32)
    private String username;

    @Size(min = 8, max = 16)
    private String password;

    @Size(min = 3, max = 10)
    private String firstName;

    @Size(min = 3, max = 10)
    private String lastName;

    @Pattern(regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;

    private Role role;
}
