package ru.skypro.homework.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;

import java.io.IOException;

public interface AdService {
    public AdsDTO getAllAds();

    public ExtendedAd getAd(Integer adId);

    public AdsDTO getAdsMe(Authentication authentication);

    public AdDTO addAd(CreateOrUpdateAd properties, MultipartFile image, String userName) throws IOException;

    public AdDTO updateAd(Integer adId, CreateOrUpdateAd properties);

    public String updateImage(Integer adId, MultipartFile image) throws IOException;

    public void delete(Integer adId) throws IOException;

}
