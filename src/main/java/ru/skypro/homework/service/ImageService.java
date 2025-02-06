package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.model.Image;

import java.io.IOException;

public interface ImageService {
    public Image uploadAdImage(CreateOrUpdateAd properties, MultipartFile image) throws IOException;

    public Integer findImageIdByImagePath(String imagePath);

    public String updateAdImage(Integer imageId, MultipartFile image) throws IOException;

    void deleteImage(Integer imageId);

    @org.springframework.transaction.annotation.Transactional
    Image saveImage(MultipartFile imageFile) throws IOException;
}
