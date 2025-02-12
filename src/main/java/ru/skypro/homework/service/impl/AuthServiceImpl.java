package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.config.MyUserDetailsService;
import ru.skypro.homework.dto.user.Register;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final MyUserDetailsService myUserDetailsService;
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserServiceImpl userService;

    public AuthServiceImpl(MyUserDetailsService myUserDetailsService,
                           PasswordEncoder passwordEncoder,
                           UserRepository userRepository,
                           UserMapper userMapper,
                           UserServiceImpl userService) {
        this.myUserDetailsService = myUserDetailsService;
        this.encoder = passwordEncoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @Override
    public boolean login(String userName, String password) {
        if (userService.findByUsername(userName).isEmpty()) {
            return false;
        }
        UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        if (userService.findByUsername(register.getUsername()).isPresent()) {
            return false;
        }
        register.setPassword(encoder.encode(register.getPassword()));
        userRepository.save(userMapper.registerToUser(register));
        return true;
    }
}
