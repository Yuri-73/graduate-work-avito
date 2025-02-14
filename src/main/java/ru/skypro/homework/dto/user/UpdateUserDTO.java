package ru.skypro.homework.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Класс для обновления данных пользователя.
 * <p>
 * Класс {@code UpdateUserDTO} используется для передачи данных, которые могут быть обновлены
 * пользователем, такие как имя (firstName), фамилия (lastName) и номер телефона (phone).
 * </p>
 * @author Yuri-73
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {

    @Size(min = 3, max = 10)
    private String firstName;

    @Size(min = 3, max = 10)
    private String lastName;

    @Pattern(regexp = PhonePattern.RU_PATTERN)
    private String phone;
}
