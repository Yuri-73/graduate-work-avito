package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.exception.ImageSaveException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor

@Transactional
public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${ad.image.path}")
    private String imagePath;

    @Value("${avatar.dir.path}")
    private String avatarPath;

    @Override
    public Image uploadAdImage(CreateOrUpdateAd properties, MultipartFile image) throws IOException {
        createDirectory();
        Path filePath = Path.of(imagePath, properties.getTitle() + properties.getPrice() + "." + image.getOriginalFilename());
        image.transferTo(filePath);

        Image image1 = new Image();
        image1.setImagePath(filePath.toString());
        imageRepository.save(image1);
        image1.setId(findImageIdByImagePath(filePath.toString()));
        return image1;
    }

    @Override
    public Integer findImageIdByImagePath(String imagePath) {
        return imageRepository.findIdByImagePath(imagePath).orElse(-1);
    }

    @Override
    public String updateAdImage(Integer imageId, MultipartFile image) throws IOException {
        if (!imageRepository.existsById(imageId)) {
            throw new ImageNotFoundException(imageId);
        }
        Image image1 = imageRepository.findById(imageId).orElse(null);
        Path filePath = Path.of(image1.getImagePath());
        image.transferTo(filePath);


        return filePath.toString();
    }


    private String getExtensions(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void createDirectory() throws IOException {
        Path path = Path.of(imagePath);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    /**
     * Удаление изображения
     *
     * @param imageId
     */
    @Override
    public void deleteImage(Integer imageId) {
        if (imageRepository.existsById(imageId)) {
            imageRepository.deleteById(imageId);
        }
    }

    @Override
    public Image saveImage(MultipartFile image) throws IOException {
        createDirectoryAvatar();
        Path filePath = Path.of(avatarPath, "." + image.getOriginalFilename());
        image.transferTo(filePath);

        Image image1 = new Image();
        image1.setImagePath(filePath.toString());
        System.out.println("image1.getImagePath() - " + image1.getImagePath());
        imageRepository.save(image1);
        image1.setId(findImageIdByImagePath(filePath.toString()));
        return image1;

    }

    private void createDirectoryAvatar() throws IOException {
        Path path = Path.of(avatarPath);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

}
