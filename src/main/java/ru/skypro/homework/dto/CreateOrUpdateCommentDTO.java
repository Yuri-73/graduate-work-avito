package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String text;
}
