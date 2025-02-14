package ru.skypro.homework.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ConditionalOnClass
@WebMvcTest(AdsController.class)
@Import({WebSecurityConfig.class})
public class AdControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private AdServiceImpl adService;

    @MockBean
    private AdRepository adRepository;

    @SpyBean
    private UserServiceImpl userService;

    @SpyBean
    private UserMapper userMapper;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private AdMapper adMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @SpyBean
    private ImageServiceImpl imageService;

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdsController adsController;

    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        user.setImage(image);
    }

    @Test
    @WithMockUser(username = "testUser", authorities = {"USER"})
    @DisplayName("Тест на вывод всех объявлений")
    void getAllAds() throws Exception {
        mockMvc.perform(get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

//    @Test
//    @WithMockUser(username = "user") //иначе 401 Unauthorized, ведь Spring должен подложить principal в параметры
//    public void getMyAdsTest() throws Exception {
//        userRepository.save(user);
//
//        //созданный юзер содержит коллекцию объявлений, которые должны вернуться
//        Ad ad = new Ad();
//        ad.setId(222);
//        ad.setUser(user);
//        ad.setTitle("MyAd");
//        user.setAds(Collections.singletonList(ad));
//        //затыкаем userRepository.findByUsername
//        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
//
//        mockMvc.perform(get("/ads/me"))
//                .andExpect(status().isOk())
//                .andExpect(result -> {
//                    String content = result.getResponse().getContentAsString();
//                    AdsDTO actualAdsDtoOut = objectMapper.readValue(content, AdsDTO.class);
//                    //проверяем, что к нам вернулся список, который мы пришили к пользователю
//                    assertThat(actualAdsDtoOut).isEqualTo(adMapper.adsToAdsDto(user.getAds()));
//                });
//    }
}
