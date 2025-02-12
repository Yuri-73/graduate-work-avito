package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.exception.ImageNotFoundException;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Principal;
import java.util.UUID;

/**
 * {@link Класс} ImageServiceImpl реализации логики работы с изображениями <br>
 *
 * @author Chowo
 */

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

    private final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    /**
     * Сохранение картинки объявления.
     * </p>
     * @param image файл изображения
     * @param properties Dto объекта CreateOrUpdateAd.
     * @return Image
     */
    @Override
    public Image uploadAdImage(CreateOrUpdateAd properties, MultipartFile image) throws IOException {
        logger.info("ImageService uploadAdImage is running");
        createDirectory();
        Path filePath = Path.of(imagePath, properties.getTitle() + properties.getPrice() + "." + image.getOriginalFilename());
        image.transferTo(filePath);

        Image image1 = new Image();
        image1.setImagePath(filePath.toString());
        imageRepository.save(image1);
        image1.setId(findImageIdByImagePath(filePath.toString()));
        return image1;
    }

    /**
     * Выведение id картинки из БД по пути файла.
     * @param imagePath файл изображения
     * @return Integer
     * @throws ImageNotFoundException если такого пути не обнаружено в БД
     */
    @Override
    public Integer findImageIdByImagePath(String imagePath) {
        logger.info("ImageService findImageIdByImagePath is running");
        return imageRepository.findByImagePath(imagePath).orElseThrow(ImageNotFoundException::new).getId();
    }

    /**
     * Изменение картинки в БД.
     * @param imageId идентификатор изображения
     * @param image файл изображения
     * @return String путь к изменённому файлу
     * @throws ImageNotFoundException если такой image не обнаружено в БД
     */
    @Override
    public String updateAdImage(Integer imageId, MultipartFile image) throws IOException {
        logger.info("ImageService updateAdImage is running");
        if (!imageRepository.existsById(imageId)) {
            throw new ImageNotFoundException(imageId);
        }
        Image image1 = imageRepository.findById(imageId).orElseThrow(() -> new ImageNotFoundException(imageId));
        Path filePath = Path.of(image1.getImagePath());
        Files.deleteIfExists(filePath);
        image.transferTo(filePath);

        return filePath.toString();
    }

    private String getExtensions(String fileName) {
        logger.info("ImageService getExtensions is running");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private void createDirectory() throws IOException {
        logger.info("ImageService createDirectory is running");
        Path path = Path.of(imagePath);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    /**
     * Удаление изображения.
     * </p>
     * @param imageId идентификатор изображения
     */
    @Override
    public void deleteImage(Integer imageId) {
        logger.info("ImageService deleteImage is running");
        if (imageRepository.existsById(imageId)) {
            imageRepository.deleteById(imageId);
        }
        logger.info("Image deleted [{}]", imageId);
    }

    /**
     * Сохранение аватарки.
     * </p>
     * @param image файл изображения
     * </p>
     * @return Image
     */
    @Override
    public Image saveImage(MultipartFile image, Principal principal) throws IOException {
        logger.info("ImageService saveImage is running");
        createDirectoryAvatar();
        Path filePath = Path.of(avatarPath, principal.getName()
                .substring(0, principal.getName()
                .lastIndexOf("@")) + "." + getExtension(image.getOriginalFilename()));

        image.transferTo(filePath);

        Image image1 = new Image();
        image1.setImagePath(filePath.toString());
        imageRepository.save(image1);
        logger.info("Image [{}] saved ", image1.getId());
        return image1;
    }

    private void createDirectoryAvatar() throws IOException {
        logger.info("ImageService createDirectoryAvatar is running");
        Path path = Path.of(avatarPath);
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    private String getExtension(String originalPath) {
        logger.info("ImageService getExtension is running");
        return originalPath.substring(originalPath.lastIndexOf(".") + 1);
    }

    /**
     * Получение изображения из БД.
     * <p>
     * @param id идентификатор изображения
     * </p>
     * @return byte[]
     */
    public byte[] getImageBytes(Integer id) throws IOException {
        logger.info("ImageService getImageBytes is running");
        Path imagePath = Path.of(imageRepository.findById(id).orElseThrow(() -> new ImageNotFoundException(id)).getImagePath());
        return Files.readAllBytes(imagePath);
    }
}
