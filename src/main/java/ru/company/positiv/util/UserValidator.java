package ru.company.positiv.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.company.positiv.dto.RegistrationDTO;
import ru.company.positiv.repositories.UserRepositories;

/**
 * Валидатор для проверки данных регистрации пользователя.
 * Проверяет уникальность логина и электронной почты перед сохранением.
 */
@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {

    private final UserRepositories userRepositories;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegistrationDTO.class.equals(clazz);
    }

    /**
     * Выполняет валидацию объекта RegistrationDTO.
     * Проверяет, что логин и email не заняты другими пользователями.
     */
    @Override
    public void validate(Object target, Errors errors) {
        RegistrationDTO registrationDTO = (RegistrationDTO) target;
        if (userRepositories.findByEmail(registrationDTO.getEmail()).isPresent()) {
            errors.rejectValue("email", "", "Данная электронная почта уже занята другим пользователем!");
        }
        if (userRepositories.findByLogin(registrationDTO.getLogin()).isPresent()) {
            errors.rejectValue("login", "", "Данный логин уже занят другим пользователем!");
        }
    }
}