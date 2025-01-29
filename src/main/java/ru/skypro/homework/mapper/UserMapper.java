package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.user.Register;
import ru.skypro.homework.dto.user.UserDTO;
import ru.skypro.homework.model.User;

@Component
public class UserMapper {
    /**
     * Метод преобразует Dto UserDTO и Dto Register в объект класса User.
     *
     * @param register Dto Register.
     * @param userDTO Dto UserDTO.
     * @return объект класса User.
     */
    public User userDtoAndRegisterToUser(UserDTO userDTO) {
        User newUser = new User();

        newUser.setId(userDTO.getId());
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        newUser.setPhone(userDTO.getPhone());
        newUser.setRole(userDTO.getRole());
        newUser.setImage(userDTO.getImage());

        return newUser;
    }

    /**
     * Метод преобразует объект класса User в Dto UserDto.
     * @param user объект класса User.
     * @return Dto UserDto.
     */
    public UserDTO userToUserDto(User user) {
        UserDTO userDto = new UserDTO();

        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhone(user.getPhone());
        userDto.setRole(user.getRole());
        userDto.setImage(user.getImage());

        return userDto;
    }
}

