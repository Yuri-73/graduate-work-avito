package ru.skypro.homework.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Data Transfer Object (DTO) для представления объявления.
 * <p>
 * Класс {@code CommentsDTO} предназначен для удобной упаковки и передачи нескольких DTO комментариев {@code CommentDTO},
 * предоставления дополнительной информации, такой как общее количество комментариев в этом наборе
 * </p>
 * <p>
 * Этот класс полезен для сценариев, где требуется передать список комментариев объявления, например, при пагинации
 * или в ответах API, где необходимо вместе со списком предоставить дополнительные метаданные.
 * </p>
 *
 * @author Chowo
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private Integer count;
    private List<CommentDTO> results;
}
