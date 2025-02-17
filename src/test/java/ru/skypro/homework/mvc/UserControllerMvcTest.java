package ru.skypro.homework.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.internal.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.controller.UserController;
import ru.skypro.homework.dto.Role;

import ru.skypro.homework.dto.user.NewPassword;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.service.impl.ImageServiceImpl;
import ru.skypro.homework.service.impl.UserServiceImpl;

import java.security.Principal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hibernate.tool.schema.SchemaToolingLogging.LOGGER;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = UserController.class)
@Import(WebSecurityConfig.class)
public class UserControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @SpyBean
    private UserServiceImpl userService;

    @SpyBean
    private ImageServiceImpl imageService;

    @MockBean
    private ImageRepository imageRepository;

    @SpyBean
    private UserMapper userMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserController userController;

    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User();
        user.setId(1);
        user.setUsername("user@test");
        user.setPassword(passwordEncoder.encode("password"));
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Image image = new Image();
        user.setImage(image);
    }

    @Test
    @WithMockUser(username = "user@test")
    @DisplayName("Тест на обновление пароля")
    public void setPasswordTest() throws Exception {
        NewPassword newPasswordDto = new NewPassword();
        newPasswordDto.setCurrentPassword("password");
        newPasswordDto.setNewPassword("newPassword");

        String username = user.getUsername();

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));


        mockMvc.perform(
                        post("/users/set_password")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(newPasswordDto))
                                .principal(principal))
                .andExpect(status().isOk());

        verify(userService, times(1))
                .setPassword(eq(newPasswordDto), any(Principal.class));
    }

    @Test
    @WithMockUser(username = "user@test")
    @DisplayName("Тест на получение информации об авторизованном пользователе")
    public void getUserTest() throws Exception {
        UserDTO expected = userMapper.userToUserDto(user);

        String username = user.getUsername();

        Principal principal = new Principal() {
            @Override
            public String getName() {
                return username;
            }
        };
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        mockMvc.perform(
                        get("/users/me")
                                .contentType(MediaType.APPLICATION_JSON)
                                .principal(principal))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UserDTO userDto = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            UserDTO.class
                    );
                    assertThat(userDto).isEqualTo(expected);
                });
        verify(userService, times(1)).getUser(any(Principal.class));
    }

    @Test
    @WithMockUser(username = "user@test")
    @DisplayName("Тест на обновление информации об авторизованном пользователе")
    public void updateUserTest() throws Exception {
        UpdateUserDTO expected = new UpdateUserDTO();
        expected.setFirstName("Petr");
        expected.setLastName("Petrov");
        expected.setPhone("+7985-111-11-11");

        String username = user.getUsername();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        mockMvc.perform(
                        patch("/users/me")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(expected)))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UpdateUserDTO updateUserDtoResult = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            UpdateUserDTO.class
                    );
                    assertThat(updateUserDtoResult).isEqualTo(expected);
                });
        verify(userService, times(1)).updateUser(eq(expected), any(Principal.class));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "user@test")
    void updateUserImage() {
        MockMultipartFile file = new MockMultipartFile("test.jpg", "message".getBytes());
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        mockMvc.perform(
                multipart("/users/me/image")
                        .part(new MockPart("image", "test.jpg", "message".getBytes())))
                        .andExpect(status().isOk()
        );
    }
}

//    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity updateUserImage(@RequestParam MultipartFile image, Principal principal) {
//        LOGGER.info(String.format("Получен запрос для updateUserImage: image = %s, " + "user = %s", image, principal.getName()));
//        userService.updateUserImage(image, principal);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

