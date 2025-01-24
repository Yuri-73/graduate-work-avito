package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) для представления объявления.
 * <p>
 * Класс {@code CreateOrUpdateCommentDTO} предназначен передачи текста комментария при создании или обновлении
 * комментария
 * </p>
 * <p>
 * Этот класс содержит в себе текст комментария.
 * Предназначен для упрощения процессов создания или изменения комментариев объявлений.
 * </p>
 *
 * @author Chowo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateCommentDTO {

    @Size(min = 8, max = 64)
    private String text;
}
