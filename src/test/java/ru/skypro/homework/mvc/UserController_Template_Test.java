package ru.skypro.homework.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import ru.skypro.homework.config.MyUserDetailsService;
import ru.skypro.homework.config.UserSecurityDTO;
import ru.skypro.homework.controller.AuthController;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.PhonePattern;
import ru.skypro.homework.dto.user.Register;
import ru.skypro.homework.dto.user.UserDTO;
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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

//    @Test
//    public void setPasswordTest() throws Exception {
//        NewPassword newPasswordDto = new NewPassword();
//        newPasswordDto.setCurrentPassword("password");
//        newPasswordDto.setNewPassword("newPassword");
//
//        restTemplate.getForObject("http://localhost:" + port + "/users/set_password", newPasswordDto);
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

    @Test
    public void getUserTest() throws Exception {
        //test:
        var userDTO = restTemplate.postForObject("/register", register, Register.class);

        Principal principal1 = new Principal() {
            @Override
            public String getName() {
                return "wasja@gmail.com";
            }
        };
//        var result = restTemplate.getRestTemplate("http://localhost:" + port + "/users/me", principal1, UserController.class);

        ResponseEntity<UserDTO> result = restTemplate.exchange(
                "http://localhost:" + port + "/users/me",
                HttpMethod.GET,
                (HttpEntity<?>) principal1,
                UserDTO.class
        );

        org.assertj.core.api.Assertions.assertThat(result).isNotNull();
        org.assertj.core.api.Assertions.assertThat(result.getBody().getEmail()).isEqualTo(userDTO.getUsername());
    }

//    @GetMapping("/me")
//    public ResponseEntity<UserDTO> getUser(Principal principal) {
//        LOGGER.info(String.format("Получен запрос для getUser: user = %s", principal.getName()));
//        return ResponseEntity.ok().body(userService.getUser(principal));
//    }
}


