package ru.skypro.homework.dto.comment;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Класс для создания или обновления комментария.
 * <p>
 * Класс {@code CreateOrUpdateComment} используется для передачи данных
 * при создании или обновлении комментария. Он включает в себя поле "text",
 * представляющее текст комментария.
 * </p>
 * @author Chowo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateCommentDTO {

    @Size(min = 8, max = 64)
    private String text;
}
