package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Chowo
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentsNotFoundException extends RuntimeException {
    public CommentsNotFoundException(Integer adId) {
        super("Comments for ad with Id: " + adId + " not found");
    }
    public CommentsNotFoundException() {
        super("Comments for not found");
    }
}
