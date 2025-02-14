package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.User;
import ru.skypro.homework.service.impl.AdServiceImpl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@AutoConfigureMockMvc
@WebMvcTest(AdsController.class)
class AdsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdServiceImpl adService;

    @Autowired
    private ObjectMapper objectMapper;

    User user;

    @BeforeEach
    public void init() {
        user = new User();
        user.setFirstname("Ivan");
        user.setId(1);
        user.setLastname("Smith");
        user.setRole(Role.USER);
        user.setPhone("+7984521000");
        user.setUsername("test@test");
    }


    @Test
    @WithMockUser(username = "test@test", authorities = "USER")
    void getAllAds() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/ads")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test", authorities = "USER")
    void getAds() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test", authorities = "USER")
    void getAdsMe() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/ads/me")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "test@test", authorities = "USER")
    void addAd() throws Exception {

//        CreateOrUpdateAd properties = new CreateOrUpdateAd("test", 15000, "Test Ad description");
//        MockMultipartFile image = new MockMultipartFile("test image",
//                "testing.jpg",
//                MediaType.IMAGE_JPEG_VALUE,
//                "testing content".getBytes());
//
//
//        Principal principal = () -> "test@test";
//
//
////        AdDTO adDTO = new AdDTO();
////        adDTO.setPk(1);
////        adDTO.setTitle("Test Ad");
//        when(adService.addAd(any(CreateOrUpdateAd.class), eq(image), eq("test@test"))).thenReturn(ADFORTESTS);
//
//        mockMvc.perform(multipart("/ads")
//                        .file(new MockMultipartFile("properties", "",
//                                MediaType.APPLICATION_JSON_VALUE,
//                                objectMapper.writeValueAsBytes(properties)))
//                        .file(image)
//                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE).principal(principal))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.pk").value(1))
//                .andExpect(jsonPath("$.title").value("test"))
//                .andExpect(jsonPath("$.price").value(15000));
    }

    @Test
    @WithMockUser(username = "test@test", authorities = "USER")
    void updateAds() throws Exception {

//        User user = new User();
//        user.setFirstname("");
//
//        Integer adId = 1;
//        CreateOrUpdateAd updateAd = new CreateOrUpdateAd();
//        updateAd.setTitle("Updated Title");
//        updateAd.setDescription("Updated Description");
//        updateAd.setPrice(20000);
//
//        AdDTO adDTO = ADFORTESTS;
//        adDTO.setAuthor(user.getId());
//        adDTO.setTitle("Updated Title");
//        adDTO.setPrice(20000);
//
//
//        when(adService.updateAd(eq(adId), any(CreateOrUpdateAd.class))).thenReturn(adDTO);
//
//        mockMvc.perform(MockMvcRequestBuilders.patch("/ads/{id}", adId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updateAd)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.pk").value(adId))
//                .andExpect(jsonPath("$.title").value("Updated Title"))
//                .andExpect(jsonPath("$.price").value(20000));


    }

    @Test
    void updateImage() {
    }

    @Test
    void removeAd() {
    }
}