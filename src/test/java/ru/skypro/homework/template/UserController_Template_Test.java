package ru.skypro.homework.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.Register;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserController_Template_Test {

    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserServiceImpl userService;

    @MockBean
    ImageServiceImpl imageService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    UserMapper mapper;

    @Autowired
    AuthService authService;

    private UserDTO userDTO;
    Register register;

    @BeforeEach
    public void beforeEach() {
        userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("test@test.com");
        userDTO.setFirstName("Ivan");
        userDTO.setLastName("Ivanov");
        userDTO.setPhone("+7852-123-45-67");
        userDTO.setRole(Role.USER);
        userDTO.setImage("/image");

        register = new Register();
        register.setUsername("wasja@gmail.com");
        register.setPassword("ssssssss");
        register.setFirstName("Вася");
        register.setLastName("Иванов");
        register.setPhone("+7 800 5550000");
        register.setRole(Role.USER);
    }

    @Test
    void contextLoads() throws Exception {
        org.assertj.core.api.Assertions.assertThat(userController).isNotNull();
    }

    @Test
    @DisplayName("Тест на получение информации об авторизованном пользователе")
    @WithMockUser(username = "Test_User")
    void getUserTest(){
        User user = new User();
        user.setUsername("Test_User");
        user.setPassword(passwordEncoder.encode("password"));
        user.setPhone("phone");
        user.setLastname("Test");
        user.setFirstname("FirstTest");
        user.setRole(Role.USER);
        userRepository.save(user);

        HttpHeaders httpHeaders = new HttpHeaders();
        String auth = new String(Base64.getEncoder().encode("Test_User:password".getBytes(StandardCharsets.UTF_8)));
        httpHeaders.add("Authorization", "Basic " + auth);
        HttpEntity<Map<String, String>> authorization = new HttpEntity<>(httpHeaders);
        ResponseEntity<UserDTO> forObject = restTemplate
                .exchange("http://localhost:" + port + "/users/me", HttpMethod.GET, authorization, UserDTO.class);
        assertEquals(user.getUsername(), forObject.getBody().getEmail());
    }
}


