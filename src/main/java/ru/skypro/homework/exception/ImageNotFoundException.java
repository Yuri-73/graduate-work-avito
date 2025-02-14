package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Yuri-73
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(Integer imageId) {
        super("Картинка: " + imageId + " не найдена");
    }

    public ImageNotFoundException() {
        super("Картинка не найдена");
    }

}
