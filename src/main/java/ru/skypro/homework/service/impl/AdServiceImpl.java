package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.ImageService;

import javax.transaction.Transactional;
import java.io.IOException;

@Slf4j
@Transactional
@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final AdMapper adMapper;
    private final ImageService imageService;

    @Value("${ad.image.path}")
    private String imagePath;


    public AdServiceImpl(AdRepository adRepository, UserRepository userRepository, ImageRepository imageRepository, AdMapper adMapper, ImageService imageService) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.adMapper = adMapper;
        this.imageService = imageService;
    }


    @Override
    public AdsDTO getAllAds() {
        return adMapper.adsToAdsDto(adRepository.findAll());
    }

    @Override
    public ExtendedAd getAd(Integer adId) {
        return adMapper.adToExtendedDtoOut(adRepository.findById(adId).orElse(null));
    }

    @Override
    public AdsDTO getAdsMe(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).orElse(null);
        if (user == null) {
            return null;
        }
        return adMapper.adsToAdsDto(adRepository.findAllByUserId(user.getId()).get());
    }

    @Override
    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image, String userName) throws IOException {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException(userName));
//        Image image1 = imageService.uploadAdImage(properties, image);
        Image dud = null;
        Ad ad = adMapper.createAd(properties, user, dud);
        adRepository.save(ad);
        return adMapper.adToAdDto(ad);
    }

    @Override
    public AdDTO updateAd(Integer adId, CreateOrUpdateAd properties) {
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException(adId);
        }
        Ad ad = adRepository.findById(adId).orElse(null);
        ad.setTitle(properties.getTitle());
        ad.setPrice(properties.getPrice());
        ad.setDescription(properties.getDescription());
        adRepository.save(ad);
        return adMapper.adToAdDto(ad);
    }

    @Override
    public String updateImage(Integer adId, MultipartFile image) throws IOException {
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException(adId);
        }
        Ad ad = adRepository.findById(adId).orElse(null);
//        return imageService.updateAdImage(ad.getImage().getId(), image);
        return null;
    }

    @Override
    public void delete(Integer adId) {
        if (!adRepository.existsById(adId)) {
            throw new AdNotFoundException(adId);
        }

        adRepository.deleteById(adId);
    }

    public String getAdUserName(Integer adId) {
        return adRepository.findById(adId).orElseThrow(()->new AdNotFoundException(adId)).getUser().getUsername();
    }
}
