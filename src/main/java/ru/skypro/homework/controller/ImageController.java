package ru.skypro.homework.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;

/**
 * @author Chowo
 */

@RestController
@RequestMapping("images")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ImageController {

    private final ImageServiceImpl imageService;

    @Operation(summary = "Получение изображения")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK: возвращает изображение",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "NOT_FOUND: изображение не найдено",
                    content = @Content
            )
    })
    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) {
        try {
            byte[] image = imageService.getImageBytes(id);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

