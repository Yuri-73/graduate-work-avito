package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Chowo
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdNotFoundException extends RuntimeException {
    public AdNotFoundException(Integer adId) {
        super("Ad with Id: " + adId + " not found");
    }
}
