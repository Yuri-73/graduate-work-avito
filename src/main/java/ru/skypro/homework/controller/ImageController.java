package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.service.impl.ImageServiceImpl;

import java.io.IOException;

@RestController
@RequestMapping("images")
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@Validated
@Slf4j
public class ImageController {

    private final ImageServiceImpl imageService;

    @GetMapping(value = "/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Integer id) throws IOException {
        try {
            byte[] image = imageService.getImageBytes(id);
            return ResponseEntity.ok(image);
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

