package ru.skypro.homework;

import lombok.RequiredArgsConstructor;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;

@RequiredArgsConstructor
public class AdMapper {
    private final UserRepository userRepository;
    /**
     * Метод преобразует Dto AdDTO в объект класса Ad.
     * @param adDTO Dto AdDTO.
     * @return объект класса Ad.
     */
    public Ad adDtoToAd(AdDTO adDto, ExtendedAd extendedAd) {
        Ad newAd = new Ad();

        newAd.setId(adDto.getPk());
        newAd.setTitle(adDto.getTitle());
        newAd.setPrice(adDto.getPrice());
        newAd.setImage(adDto.getImage());

        newAd.setDescription(extendedAd.getDescription());
        return newAd;
    }

    /**
     * Метод преобразует объект класса Ad в Dto AdDTO.
     * @param ad объект класса Ad.
     * @return Dto AdDTO.
     */
    public AdDTO adToAdDto(Ad ad) {
        AdDTO adDTO = new AdDTO();

        adDTO.setPk(ad.getId());
        adDTO.setTitle(ad.getTitle());
        adDTO.setPrice(ad.getPrice());
        adDTO.setImage("/ads/" + ad.getId() + "/image");
        adDTO.setAuthor(ad.getUser().getId());
        return adDTO;
    }

    /**
     * Метод преобразует объект класса Ad в Dto ExtendedAd.
     * @param ad объект класса Ad.
     * @return Dto ExtendedAd.
     */
    public ExtendedAd toAdExtendedDtoOut(Ad ad, User user) {
        ExtendedAd extendedAd = new ExtendedAd();

        extendedAd.setPk(ad.getId());
        extendedAd.setAuthorFirstName(user.getFirstname());
        extendedAd.setAuthorLastName(user.getLastname());
        extendedAd.setEmail(user.getPassword());
        extendedAd.setPhone(user.getPhone());
        extendedAd.setTitle(ad.getTitle());
        extendedAd.setPrice(ad.getPrice());
        extendedAd.setDescription(ad.getDescription());
        extendedAd.setImage(ad.getImage());
        return extendedAd;
    }
}

