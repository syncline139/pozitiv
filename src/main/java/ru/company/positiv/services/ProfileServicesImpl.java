package ru.company.positiv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServicesImpl implements ProfileServices {

    private final UserRepositories userRepositories;

    @Override
    public void save(UserDTO userDTO) {
        // Получаем логин текущего пользователя из контекста безопасности
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();

        // Загружаем пользователя из БД по логину
        User user = userRepositories.findByLogin(currentLogin)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Обновляем только поле city из DTO
        user.setCity(userDTO.getCity());

        // Сохраняем обновленного пользователя в базе
        userRepositories.save(user);
    }
}
