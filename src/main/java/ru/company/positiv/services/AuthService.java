package ru.company.positiv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    /**
     * Регестрация пользователя, его пароль шифруеться,
     * выдаеться роль и вычесляеться дата регестрации!
     */
    @Transactional
    public void register(User user) {
        String rawPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRole("USER");
        user.setRegisteredAt(LocalDateTime.now());
        userRepositories.save(user);
    }



}
