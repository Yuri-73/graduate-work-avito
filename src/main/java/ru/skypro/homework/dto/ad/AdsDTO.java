package ru.skypro.homework.dto.ad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Класс-контейнер для группы DTO объявлений.
 * <p>
 * Класс {@code AdsDTO} предназначен для удобной упаковки и передачи нескольких DTO объявлений,
 * {@code AdDTO}, а также для предоставления дополнительной информации, такой как общее количество
 * объявлений в этом наборе.
 * </p>
 * <p>
 * Этот класс полезен для сценариев, где требуется передать список объявлений, например, при пагинации
 * или в ответах API, где необходимо вместе со списком предоставить дополнительные метаданные.
 * </p>
 *
 * @author Yuri-73
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdsDTO {

    private Integer count;

    private List<AdDTO> results;
}
