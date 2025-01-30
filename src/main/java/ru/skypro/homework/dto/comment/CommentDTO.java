package ru.skypro.homework.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Size;

/**
 * Класс DTO (Data Transfer Object) для представления комментария в приложении.
 * <p>
 * Этот класс используется для передачи данных о комментариях между различными частями
 * приложения, такими как контроллеры и представления. Он содержит информацию о комментарии,
 * такую как идентификатор автора, изображение автора, имя автора, дата создания,
 * идентификатор комментария и текст комментария.
 * </p>
 * <p>
 * Аннотация {@code @Data} от Lombok автоматически генерирует методы для доступа к полям,
 * такие как геттеры и сеттеры, а также методы {@code equals}, {@code hashCode} и {@code toString}.
 * </p>
 * <p>
 * Поля данного класса представляют атрибуты комментария и используются для отображения
 * информации о комментарии.
 * </p>
 * @author Chowo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private Integer pk;
    @Size(min = 8, max = 64)
    private String text;
}
