package ru.skypro.homework.mapper;

import lombok.RequiredArgsConstructor;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Yuri-73
 */
@RequiredArgsConstructor
public class AdMapper {

    private final UserRepository userRepository;

    /**
     * Метод преобразует Dto CreateOrUpdateAd в объект класса Ad.
     * @param createOrUpdateAd Dto, user, image.
     * @return объект класса Ad.
     */
    public Ad createOrUpdateAdToAd(CreateOrUpdateAd createOrUpdateAd, User user, Image image) {
        if (createOrUpdateAd == null) {
            throw new IllegalArgumentException("Попытка конвертировать createOrUpdateAd == null");
        }
        Ad newAd = new Ad();

        newAd.setTitle(createOrUpdateAd.getTitle());
        newAd.setPrice(createOrUpdateAd.getPrice());
        newAd.setDescription(createOrUpdateAd.getDescription());
        newAd.setUser(user);
        newAd.setImage(image);

        return newAd;
    }

//    public Ad toEntity(AdDtoIn adDtoIn, Principal principal) {
//        Ad ad = new Ad();
//        String username = principal.getName();
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UserNotFoundException(username));
//        ad.setUser(user);
//        ad.setTitle(adDtoIn.getTitle());
//        ad.setPrice(adDtoIn.getPrice());
//        ad.setDescription(adDtoIn.getDescription());
//        return ad;
//    }



    /**
     * Метод преобразует Dto CreateOrUpdateAd в объект класса Ad.
     * @param ad, createOrUpdateAd Dto, user, image.
     * @return объект класса Ad.
     */
    public Ad createOrUpdateAdToAd(Ad ad, CreateOrUpdateAd createOrUpdateAd) {
        if (createOrUpdateAd == null) {
            throw new IllegalArgumentException("Попытка конвертировать createOrUpdateAd == null");
        }
        Ad newAd = new Ad();

        newAd.setTitle(createOrUpdateAd.getTitle());
        newAd.setPrice(createOrUpdateAd.getPrice());
        newAd.setDescription(createOrUpdateAd.getDescription());
        newAd.setUser(ad.getUser());
        newAd.setImage(ad.getImage());
        newAd.setId(ad.getId());

        return newAd;
    }

    /**
     * Метод преобразует объект класса Ad в Dto AdDTO.
     * @param ad объект класса Ad.
     * @return Dto AdDTO.
     */
    public AdDTO adToAdDto(Ad ad) {
        if (ad == null) {
            throw new IllegalArgumentException("Попытка конвертировать ad == null");
        }
        AdDTO adDTO = new AdDTO();

        adDTO.setPk(ad.getId());
        adDTO.setTitle(ad.getTitle());
        adDTO.setPrice(ad.getPrice());
        adDTO.setImage(ad.getImage().getImagePath());
        adDTO.setAuthor(ad.getUser().getId());
        return adDTO;
    }

    /**
     * Метод преобразует объект класса Ad в Dto ExtendedAd.
     * @param ad объект класса Ad.
     * @return Dto ExtendedAd.
     */
    public ExtendedAd adToExtendedDtoOut(Ad ad) {
        if (ad == null) {
            throw new IllegalArgumentException("Попытка конвертировать ad == null");
        }
        ExtendedAd extendedAd = new ExtendedAd();

        extendedAd.setPk(ad.getId());
        extendedAd.setTitle(ad.getTitle());
        extendedAd.setPrice(ad.getPrice());
        extendedAd.setDescription(ad.getDescription());
        extendedAd.setImage(ad.getImage().getImagePath());

        extendedAd.setAuthorFirstName(ad.getUser().getFirstname());
        extendedAd.setAuthorLastName(ad.getUser().getLastname());
        extendedAd.setEmail(ad.getUser().getPassword());
        extendedAd.setPhone(ad.getUser().getPhone());

        return extendedAd;
    }


    public AdsDTO adsToAdsDto(List<Ad> ads) {
        AdsDTO adsDTO = new AdsDTO();
        adsDTO.setCount(ads.size());
        adsDTO.setResults(ads.stream().map(this::adToAdDto).collect(Collectors.toList()));
        return adsDTO;
    }


}

