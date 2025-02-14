package ru.skypro.homework.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.IncorrectPasswordException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * {@link Класс} AdServiceImpl реализации логики работы с объявлениями <br>
 *
 * @author Chowo
 */
@Slf4j
@Transactional
@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);

    public AdServiceImpl(AdRepository adRepository, UserRepository userRepository, AdMapper adMapper, ImageService imageService) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
        this.imageService = imageService;
    }

    /**
     * Метод получения всех объявлений в БД.
     * @return AdsDTO Dto-объекты, состоящие из списка объектов AdDTO и их количества
     */
    @Override
    public AdsDTO getAllAds() {
        logger.info("AdService findAll is running");
        return adMapper.adsToAdsDto(adRepository.findAll());
    }

    /**
     * Метод нахождения объявления по его id в БД.
     * @param adId идентификатор объявления в БД.
     * @return ExtendedAd Dto-объект объявления.
     */
    @Override
    public ExtendedAd getAd(Integer adId) {
        logger.info("AdService getAd is running");
        return adMapper.adToExtendedDtoOut(adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(adId)));
    }

    /**
     * Метод получения объявлений авторизованного пользователя.
     * @param authentication объект аутентификации.
     * @return AdsDTO Dto-объект списка всех объявлений пользователя.
     */
    @Override
    public AdsDTO getAdsMe(Authentication authentication) {
        logger.info("AdService getAdsMe is running");
        User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException(authentication.getName()));
        return adMapper.adsToAdsDto(adRepository.findAllByUserId(user.getId()).get());
    }

    /**
     * Метод сохранения объявления авторизованного пользователя.
     * @param properties Dto объект объявления.
     * @param image объект entity изображения.
     * @param userName логин пользователя.
     * @return AdDTO Dto-объект объявления.
     */
    @Override
    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image, String userName) throws IOException {
        logger.info("AdService createAd is running");
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
        Image image1 = imageService.uploadAdImage(properties, image);
        Ad ad = adMapper.createAd(properties, user, image1);
        adRepository.save(ad);
        log.info("Ad {} {} saved", ad.getId(), ad.getTitle());
        return adMapper.adToAdDto(ad);
    }

    /**
     * Метод обновления объявления авторизованного пользователя.
     * @param properties Dto объект объявления.
     * @param adId идентификатор объявления.
     * @param properties Dto объявления.
     * @return AdDTO Dto-объект объявления.
     */
    @Override
    public AdDTO updateAd(Integer adId, CreateOrUpdateAd properties) {
        logger.info("AdService updateAd is running");
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException(adId);
        }
        Ad ad = adRepository.findById(adId).orElse(null);
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        adRepository.save(ad);
        log.info("Ad {} {} saved", ad.getId(), ad.getTitle());
        return adMapper.adToAdDto(ad);
    }

    /**
     * Метод обновления картинки объявления пользователя.
     * @param adId идентификатор объявления.
     * @param image картинка объявления.
     * @return String путь к изменённому файлу.
     */
    @Override
    public String updateImage(Integer adId, MultipartFile image) throws IOException {
        logger.info("AdService updateImage is running");
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException(adId);
        }
        Ad ad = adRepository.findById(adId).orElse(null);
        if (ad.getImage() != null) {
            imageService.deleteImage(ad.getImage().getId());
        }

        return imageService.updateAdImage(ad.getImage().getId(), image);
    }

    /**
     * Метод удаления объявления.
     * @param adId идентификатор объявления.
     * @throws AdNotFoundException если нет такого объявления в БД.
     */
    @Override
    public void delete(Integer adId) throws IOException {
        logger.info("AdService delete is running");
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException(adId);
        }

        Ad ad = adRepository.findById(adId).orElse(null);
        Path filePath = Path.of(ad.getImage().getImagePath());
        Files.deleteIfExists(filePath);
        adRepository.deleteById(adId);
    }

    /**
     * Метод нахождения объявления.
     * @param adId идентификатор объявления.
     * @throws AdNotFoundException если нет такого объявления в БД.
     * @return String.
     */
    public String getAdUserName(Integer adId) {
        logger.info("AdService getAdUserName is running");
        return adRepository.findById(adId).orElseThrow(() -> new AdNotFoundException(adId)).getUser().getUsername();
    }
}
