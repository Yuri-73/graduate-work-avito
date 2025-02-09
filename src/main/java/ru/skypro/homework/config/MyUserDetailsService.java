package ru.skypro.homework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.homework.model.User;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.service.impl.UserServiceImpl;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserServiceImpl userService;


    /**
     * Метод для аутентификации пользователя в Spring Security. Поиск пользователя в таблице user
     * с помощью метода {@link UserServiceImpl#findByUsername(String)}. Преобразование объекта
     * класса User в объект класса UserDetails.
     *
     * @param username username пользователя.
     * @return объект класса UserDetails.
     * @throws UsernameNotFoundException если пользователь не найден.
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        return new UserSecurityDTO(user);
    }
}