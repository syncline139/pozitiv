package ru.company.positiv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;

/**
 * Сервис для загрузки данных пользователя в контексте Spring Security.
 * Реализует интерфейс UserDetailsService для предоставления информации о пользователе по его логину.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositories userRepositories;

    /**
     * Загружает данные пользователя по его логину для аутентификации в Spring Security.
     *
     * @param login логин пользователя, используемый для поиска
     * @return объект UserDetails, содержащий данные пользователя (логин, пароль, роли)
     * @throws UsernameNotFoundException если пользователь с указанным логином не найден в базе данных
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepositories.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь с данным логином не найден"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getLogin())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }
}