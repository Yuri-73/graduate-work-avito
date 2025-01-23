package ru.skypro.homework.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) для представления объявления.
 * <p>
 * Класс {@code CommentDTO} используется для передачи данных комментария объявления между слоями приложения,
 * сокращая количество передаваемой информации и скрывая детали реализации сущности {@code Comment}.
 * </p>
 * <p>
 * Этот класс включает основные данные комментария объявления, такие как идентификатор пользователя-автора, ссылку на
 * аватар автора комментария, имя автора, даты и времени создания комментария, уникальный идентификатор объявления (pk),
 * текст комментария.
 * Он предназначен для упрощения обработки и представления информации о комментариях в пользовательском интерфейсе.
 * </p>
 *
 * @author Chowo
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Integer createdAt;
    private Integer pk;
    private String text;

}
