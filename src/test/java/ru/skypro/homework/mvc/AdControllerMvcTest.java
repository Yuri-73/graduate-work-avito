package ru.skypro.homework.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.controller.AdsController;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.ad.AdDTO;
import ru.skypro.homework.dto.ad.AdsDTO;
import ru.skypro.homework.dto.ad.CreateOrUpdateAd;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentsRepository;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.impl.AdServiceImpl;
import ru.skypro.homework.service.impl.CommentServiceImpl;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ConditionalOnClass
@WebMvcTest(AdsController.class)
@Import({WebSecurityConfig.class, AdServiceImpl.class, CommentServiceImpl.class})
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

    @SpyBean
    private CommentServiceImpl commentService;

    @MockBean
    private CommentsRepository commentsRepository;

    @MockBean
    private ImageRepository imageRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdsController adsController;

    private User user;
    private Image image;

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

        image = new Image();
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
}
