package ru.skypro.homework.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.skypro.homework.dto.Role;

/**
 * Класс для представления данных о пользователе.
 * <p>
 * Класс {@code UserDTO} используется для передачи основных данных о пользователе,
 * таких как идентификатор (id), адрес электронной почты (email), имя (firstName),
 * фамилия (lastName), номер телефона (phone), роль (role) и изображение профиля (image).
 * </p>
 * @author Yuri-73
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;
}
