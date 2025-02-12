package ru.skypro.homework.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.dto.ad.ExtendedAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AdMapperTest {

    @Spy
    private AdMapper adMapper;

    private User user;
    private Image image;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("encodedPassword");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        image = new Image();
        user.setImage(image);
    }

    @Test
    public void createAdTest() {
        CreateOrUpdateAd createOrUpdateAd = new CreateOrUpdateAd();
        createOrUpdateAd.setTitle("Огурец");
        createOrUpdateAd.setPrice(200);
        createOrUpdateAd.setDescription("Свежий");

        Ad ad = new Ad();
        ad.setTitle("Огурец");
        ad.setPrice(200);
        ad.setDescription("Свежий");
        ad.setImage(image);
        ad.setUser(user);

        assertThat(adMapper.createAd(createOrUpdateAd, user, image).getTitle()).isEqualTo(ad.getTitle());
        assertThat(adMapper.createAd(createOrUpdateAd, user, image).getPrice()).isEqualTo(ad.getPrice());
        assertThat(adMapper.createAd(createOrUpdateAd, user, image).getDescription()).isEqualTo(ad.getDescription());

        assertThat(adMapper.createAd(createOrUpdateAd, user, image).getImage().getId()).isNull();
        assertThat(adMapper.createAd(createOrUpdateAd, user, image).getImage().getImagePath()).isNull();
        assertThat(adMapper.createAd(createOrUpdateAd, user, image).getUser().getLastname()).isEqualTo("Ivanov");
    }

    @Test
    public void createAd_Null_ToAdTest() {
        CreateOrUpdateAd createOrUpdateAd = null;

        Ad ad = new Ad();
        ad.setTitle(" ");
        ad.setPrice(100);
        ad.setDescription("Свежий333");
        ad.setImage(image);
        ad.setUser(user);

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> adMapper.createAd(createOrUpdateAd, user, image));
    }

    @Test
    public void adToAdDtoTest() {
        Ad ad = new Ad();
        ad.setId(1);
        ad.setTitle("Огурец");
        ad.setPrice(200);
        ad.setDescription("Свежий");
        ad.setUser(user);
        ad.setImage(image);

        AdDTO adDTO = new AdDTO();
        adDTO.setTitle("Огурец");
        adDTO.setPrice(200);
        adDTO.setPk(1);
        adDTO.setImage("/images/" +image.getImagePath());
        adDTO.setAuthor(user.getId());

        assertThat(adMapper.adToAdDto(ad)).isEqualTo(adDTO);
    }

    @Test
    public void ad_Null_ToAdDtoTest() {
        Ad ad = null;

        AdDTO adDTO = new AdDTO();
        adDTO.setTitle("Огурец");
        adDTO.setPrice(200);
        adDTO.setPk(1);
        adDTO.setImage(image.getImagePath());
        adDTO.setAuthor(user.getId());

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> adMapper.adToAdDto(ad));
    }

    @Test
    public void adToExtendedDtoOutTest() {
        Ad ad = new Ad();
        ad.setId(1);
        ad.setTitle("Огурец");
        ad.setPrice(200);
        ad.setDescription("Свежий");
        ad.setUser(user);
        ad.setImage(image);
        ad.setUser(user);

        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(1);
        extendedAd.setTitle("Огурец");
        extendedAd.setPrice(200);
        extendedAd.setDescription("Свежий");
        extendedAd.setAuthorFirstName("Ivan");
        extendedAd.setAuthorLastName("Ivanov");
        extendedAd.setEmail("username");
        extendedAd.setPhone("+7852-123-45-67");
        extendedAd.setImage("/images/" +image.getImagePath());

        assertThat(adMapper.adToExtendedDtoOut(ad)).isEqualTo(extendedAd);
    }

    @Test
    public void ad_Null_ToExtendedDtoOutTest() {
        Ad ad = null;

        ExtendedAd extendedAd = new ExtendedAd();
        extendedAd.setPk(1);
        extendedAd.setTitle("Огурец");
        extendedAd.setPrice(200);
        extendedAd.setDescription("Свежий");
        extendedAd.setAuthorFirstName("Ivan");
        extendedAd.setAuthorLastName("Ivanov");
        extendedAd.setEmail("username");
        extendedAd.setPhone("+7852-123-45-67");
        extendedAd.setImage("/images/" +image.getImagePath());

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> adMapper.adToExtendedDtoOut(ad));
    }

    @Test
    public void adsToAdsDtoTest() {
        Ad ad = new Ad();
        ad.setId(1);
        ad.setTitle("Огурец");
        ad.setPrice(200);
        ad.setDescription("Свежий");
        ad.setUser(user);
        ad.setImage(image);
        ad.setUser(user);

        List<Ad> ads = new ArrayList<>();
        ads.add(ad);
        AdsDTO adsDTO = new AdsDTO();

        adsDTO.setCount(ads.size());
        adsDTO.setResults(ads.stream().map(ad1 -> adMapper.adToAdDto(ad)).collect(Collectors.toList()));

        assertThat(adMapper.adsToAdsDto(ads)).isEqualTo(adsDTO);
    }
}

