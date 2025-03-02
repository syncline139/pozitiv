package ru.company.positiv.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.positiv.dto.PasswordChangeDTO;
import ru.company.positiv.dto.UserDTO;
import ru.company.positiv.models.User;
import ru.company.positiv.repositories.UserRepositories;
import ru.company.positiv.util.PasswordMismatchException;

/**
 * Реализация сервиса для управления профилем пользователя.
 * Предоставляет методы для обновления данных профиля и смены пароля
 */
@Service
@Transactional
@RequiredArgsConstructor
public class ProfileServicesImpl implements ProfileServices {

    private final UserRepositories userRepositories;
    private final PasswordEncoder passwordEncoder;

    /**
     * Сохраняет обновленные данные профиля пользователя (имя и город).
     * Использует текущий логин из контекста безопасности для идентификации пользователя
     *
     * @param userDTO объект DTO с новыми данными пользователя (имя и город)
     * @throws UsernameNotFoundException если пользователь с текущим логином не найден
     */
    @Override
    public void save(UserDTO userDTO) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepositories.findByLogin(currentLogin)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        user.setCity(userDTO.getCity());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        userRepositories.save(user);
    }

    /**
     * Обновляет пароль пользователя.
     * Проверяет соответствие старого пароля и при успехе шифрует и сохраняет новый пароль
     *
     * @param passwordChangeDTO объект DTO с данными старого и нового пароля
     * @throws UsernameNotFoundException если пользователь с текущим логином не найден
     * @throws PasswordMismatchException если старый пароль введен неверно
     */
    @Override
    public void savePassword(PasswordChangeDTO passwordChangeDTO) {
        String currentLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepositories.findByLogin(currentLogin)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
        if (!passwordEncoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword())) {
            throw new PasswordMismatchException("Старый пароль указан неверно!");
        }
        user.setPassword(passwordEncoder.encode(passwordChangeDTO.getNewPassword()));
        userRepositories.save(user);
    }
}