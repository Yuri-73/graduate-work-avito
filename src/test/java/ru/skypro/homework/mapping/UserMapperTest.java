package ru.skypro.homework.mapper;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.dto.user.Register;
import ru.skypro.homework.dto.user.UpdateUserDTO;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.User;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @Spy
    private UserMapper userMapper;

    @Test
    public void registerToUserTest() {

        Register register = new Register();
        register.setUsername("username");
        register.setPassword("password");
        register.setFirstName("Ivan");
        register.setLastName("Ivanov");
        register.setPhone("+7852-123-45-67");
        register.setRole(Role.USER);

        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        assertThat(userMapper.registerToUser(register)).isEqualTo(user);

        Assertions.assertThat(user.getId()).isNull();
        Assertions.assertThat(user.getImage()).isNull();
        Assertions.assertThat(user.getAds()).isNull();
        Assertions.assertThat(user.getCommentsList()).isNull();
    }

    @Test
    public void register_Null_ToUserTest() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);

        Register register = null;

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> userMapper.registerToUser(register));
    }

    @Test
    public void userToUserDTOTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);
        Image image = new Image();
        image.setId(1);
        image.setImagePath("/images/1");
        user.setImage(image);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("username");
        userDTO.setFirstName("Ivan");
        userDTO.setLastName("Ivanov");
        userDTO.setPhone("+7852-123-45-67");
        userDTO.setRole(Role.USER);
        userDTO.setImage("/images/" + user.getImage().getId());

        Assertions.assertThat(userDTO.getId()).isEqualTo(user.getId());
        Assertions.assertThat(userDTO.getEmail()).isEqualTo(user.getUsername());
        Assertions.assertThat(userDTO.getFirstName()).isEqualTo(user.getFirstname());
        Assertions.assertThat(userDTO.getLastName()).isEqualTo(user.getLastname());
        Assertions.assertThat(userDTO.getPhone()).isEqualTo(user.getPhone());
        Assertions.assertThat(userDTO.getRole()).isEqualTo(user.getRole());
        Assertions.assertThat(userDTO.getImage()).isEqualTo(user.getImage().getImagePath());

        assertThat(userMapper.userToUserDto(user)).isEqualTo(userDTO);
    }

    @Test
    public void user_Null_ToUserDTOTest() {
        User user = null;

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1);
        userDTO.setEmail("username");
        userDTO.setFirstName("Ivan");
        userDTO.setLastName("Ivanov");
        userDTO.setPhone("+7852-123-45-67");
        userDTO.setRole(Role.USER);
        userDTO.setImage("/users/me/image");

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> userMapper.userToUserDto(user));
    }

    @Test
    public void userToUpdateUserDtoTest() {
        User user = new User();
        user.setId(1);
        user.setUsername("username");
        user.setPassword("password");
        user.setFirstname("Ivan");
        user.setLastname("Ivanov");
        user.setPhone("+7852-123-45-67");
        user.setRole(Role.USER);
        Image image = new Image();
        image.setImagePath("/users/me/image");
        user.setImage(image);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("Ivan");
        updateUserDTO.setLastName("Ivanov");
        updateUserDTO.setPhone("+7852-123-45-67");

        Assertions.assertThat(updateUserDTO.getFirstName()).isEqualTo(user.getFirstname());
        Assertions.assertThat(updateUserDTO.getLastName()).isEqualTo(user.getLastname());
        Assertions.assertThat(updateUserDTO.getPhone()).isEqualTo(user.getPhone());

        assertThat(userMapper.updateUserToUserDto(user)).isEqualTo(updateUserDTO);
    }

    @Test
    public void user_Null_ToUpdateUserDtoTest() {
        User user = null;

        UpdateUserDTO updateUserDTO = new UpdateUserDTO();
        updateUserDTO.setFirstName("Ivan");
        updateUserDTO.setLastName("Ivanov");
        updateUserDTO.setPhone("+7852-123-45-67");

        org.junit.jupiter.api.Assertions.assertThrows(IllegalArgumentException.class, () -> userMapper.userToUserDto(user));
    }
}

