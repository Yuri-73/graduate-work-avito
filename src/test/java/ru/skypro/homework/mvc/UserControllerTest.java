package ru.skypro.homework.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.web.servlet.MockMvc;
import ru.skypro.homework.config.MyUserDetailsService;
import ru.skypro.homework.config.UserSecurityDTO;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import javax.sql.DataSource;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private UserController userController;

    @Autowired
    private MyUserDetailsService myUserDetailsService;

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
    UserDetailsManager manager;

    @Autowired
    UserMapper mapper;

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthService authService;

    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setId(1);
        user.setUsername("test@test.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("Ivan");
        user.setFirstname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        image.setId(1);
        image.setImagePath("/image");
        user.setImage(image);
    }

    @Test
    public void setPasswordTest() throws Exception {
        NewPassword newPasswordDto = new NewPassword();
        newPasswordDto.setCurrentPassword("password");
        newPasswordDto.setNewPassword("newPassword");

        ResponseEntity<?> response = restTemplate.postForEntity("http://localhost:" + port + "/users/set_password", newPasswordDto, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}